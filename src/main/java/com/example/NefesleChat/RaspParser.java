
package com.example.NefesleChat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

public class RaspParser {
    public static List<DaySchedule> parseSchedulePage(String type, String name) throws IOException {
        String SEARCH_URL = "https://rasp.pgups.ru/search?title=" + name + "&by=" + type;

        String BASE_URL;
        if (type.equals("group")) {
            BASE_URL = "https://rasp.pgups.ru/schedule/group/";
        } else {
            BASE_URL = "https://rasp.pgups.ru/schedule/teacher/";
        }

        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

        List<DaySchedule> schedule = new ArrayList<>();
        boolean isOddWeek = isOddWeek();

        try {
            Document searchDoc = Jsoup.connect(SEARCH_URL).get();
            Elements links = searchDoc.select("#kt_content > div.kt-container.kt-grid__item.kt-grid__item--fluid > div:nth-child(2) > div.kt-portlet__body > div > div > div > a");

            if (links.isEmpty()) {
                System.err.println("Не найдена ссылка на группу/преподавателя.");
                return null;
            }

            String relativeScheduleUrl = links.first().attr("href");
            String id = relativeScheduleUrl.substring(relativeScheduleUrl.lastIndexOf("/") + 1);
            String scheduleUrl = BASE_URL + id + "?odd=" + (isOddWeek ? "1" : "0");

            Document doc = Jsoup.connect(scheduleUrl).get();
            Elements dayTables = doc.select("div.kt-portlet__body > table.table.m-table.mb-5");

            LocalDate today = LocalDate.now();
            LocalDate monday = today.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);

            for (int i = 0; i < dayTables.size(); i++) {
                Element table = dayTables.get(i);
                LocalDate currentDate = monday.plusDays(i);
                DayOfWeek dayOfWeekEnum = currentDate.getDayOfWeek();
                java.sql.Date date = java.sql.Date.valueOf(currentDate); // Получаем дату дня недели

                String dayOfWeek = table.previousElementSibling() != null ? table.previousElementSibling().text() : table.select("td.d-none.d-md-table-cell h4").text().replace(" ", "").replace("\n", "");

                if (dayOfWeek.isEmpty()) {
                    continue;
                }
                DaySchedule daySchedule = new DaySchedule(dayOfWeek, date); //Передаем дату в DaySchedule

                Elements lessons = table.select("tbody > tr");

                for (Element lessonRow : lessons) {
                    Lesson lesson = new Lesson();

                    Elements paraNumberCell = lessonRow.select("td.d-none.d-md-table-cell div[style*='font-weight: 400']");
                    if (!paraNumberCell.isEmpty()) {
                        lesson.setParaNumber(paraNumberCell.first().text().replace("пара", "").trim());
                    }

                    // Extracting time
                    Elements timeCell = lessonRow.select("td[width='15%'] div span");
                    if (!timeCell.isEmpty()) {
                        String timeString = timeCell.first().text();
                        String[] times = timeString.split("—");
                        if (times.length == 2) {
                            String startTimeString = times[0].trim();
                            String endTimeString = times[1].trim();
                            try {
                                LocalTime startTimeLocal = LocalTime.parse(startTimeString, TIME_FORMATTER);
                                LocalTime endTimeLocal = LocalTime.parse(endTimeString, TIME_FORMATTER);

                                // Объединяем дату и время с использованием Calendar
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date); // Устанавливаем дату
                                calendar.set(Calendar.HOUR_OF_DAY, startTimeLocal.getHour());
                                calendar.set(Calendar.MINUTE, startTimeLocal.getMinute());
                                calendar.set(Calendar.SECOND, 0);
                                Date startTime = calendar.getTime();

                                calendar.setTime(date); // Сбрасываем Calendar к той же дате
                                calendar.set(Calendar.HOUR_OF_DAY, endTimeLocal.getHour());
                                calendar.set(Calendar.MINUTE, endTimeLocal.getMinute());
                                calendar.set(Calendar.SECOND, 0);
                                Date endTime = calendar.getTime();

                                lesson.setStartTime(startTime);
                                lesson.setEndTime(endTime);

                            } catch (java.time.format.DateTimeParseException e) {
                                System.err.println("Ошибка при парсинге времени: " + timeString);
                                lesson.setStartTime(null);
                                lesson.setEndTime(null);
                            }
                        } else {
                            //System.err.println("Неправильный формат времени: " + timeString);
                            lesson.setStartTime(null);
                            lesson.setEndTime(null);
                        }
                    }

                    Elements roomCell = lessonRow.select("td[width='15%'] div.text-center.mt-2 a");
                    if (!roomCell.isEmpty()) {
                        lesson.setRoom(roomCell.first().text().replace("\u00A0", " "));
                    }

                    Elements subjectCell = lessonRow.select("td.align-middle div.mb-2 span:first-child");
                    if (!subjectCell.isEmpty()) {
                        lesson.setSubject(subjectCell.first().text());
                    }

                    Elements lessonTypeCell = lessonRow.select("td.align-middle div.mb-2 span.badge");
                    if (!lessonTypeCell.isEmpty()) {
                        lesson.setLessonType(lessonTypeCell.first().text());
                    }

                    // Extracting teacher's name
                    String teacherName = null;
                    Elements teacherLink = lessonRow.select("td.align-middle div:not(.mb-2) a");
                    if (!teacherLink.isEmpty()) {
                        teacherName = teacherLink.first().text();
                    } else {
                        Elements teacherDiv = lessonRow.select("td.align-middle");
                        if (!teacherDiv.isEmpty()) {
                            teacherName = teacherDiv.first().ownText();
                            if (teacherName == null || teacherName.trim().isEmpty()) {
                                Elements innerDiv = teacherDiv.select("div:not(.mb-2)");
                                if (!innerDiv.isEmpty()) {
                                    teacherName = innerDiv.first().text();
                                }
                            }
                        }
                    }
                    lesson.setTeacher(teacherName);


                    // Extracting group information for teacher's schedule
                    if (type.equals("teacher")) {
                        Elements groupLinks = lessonRow.select("div.mt-1.mt-md-3 a.btn.btn-sm.btn-elevate.btn-secondary.btn-pill.mr-1.mb-2.py-1.px-2");
                        List<String> groups = new ArrayList<>();
                        for (Element groupLink : groupLinks) {
                            groups.add(groupLink.text());
                        }
                        lesson.setGroups(groups);
                    }
                    daySchedule.addLesson(lesson);
                }

                schedule.add(daySchedule);
            }

        } catch (IOException e) {
            System.err.println("Ошибка при подключении к странице расписания: " + e.getMessage());
            return null;
        }
        return schedule;
    }

    private static boolean isOddWeek() {
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = today.get(weekFields.weekOfWeekBasedYear());
        return weekNumber % 2 != 0;
    }

    static class DaySchedule {
        private String dayOfWeek;
        private java.sql.Date date;
        private List<Lesson> lessons = new ArrayList<>();

        public DaySchedule(String dayOfWeek, java.sql.Date date) {
            this.dayOfWeek = dayOfWeek;
            this.date = date;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public java.sql.Date getDate() {
            return date;
        }

        public List<Lesson> getLessons() {
            return lessons;
        }

        public void addLesson(Lesson lesson) {
            lessons.add(lesson);
        }
    }

    static class Lesson {
        private String paraNumber;
        private Date startTime;
        private Date endTime;
        private String room;
        private String subject;
        private String teacher;
        private String lessonType;
        private List<String> groups; // Добавляем список групп

        public Lesson() {
            this.groups = new ArrayList<>();
        }

        public String getParaNumber() {
            return paraNumber;
        }

        public void setParaNumber(String paraNumber) {
            this.paraNumber = paraNumber;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getLessonType() {
            return lessonType;
        }

        public void setLessonType(String lessonType) {
            this.lessonType = lessonType;
        }

        public List<String> getGroups() {
            return groups;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }

        @Override
        public String toString() {
            return "Lesson{" +
                    "paraNumber='" + paraNumber + '\'' +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", room='" + room + '\'' +
                    ", subject='" + subject + '\'' +
                    ", teacher='" + teacher + '\'' +
                    ", lessonType='" + lessonType + '\'' +
                    ", groups=" + groups +
                    '}';
        }
    }
}
