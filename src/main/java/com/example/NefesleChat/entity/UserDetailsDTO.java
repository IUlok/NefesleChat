package com.example.NefesleChat.entity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsDTO {
    private Integer id;

    private String firstName;

    private String lastName;

    private String patronymic;
    // Роль: Студент или преподаватель
    private String role;
    // Даты присоединения/ухода
    private Date enabledFrom;
    private Date enabledUntil;
    // Форма возмещения средств: бюджет, контракт, целевое - если роль студента
    private String reimbursement;
    // Название группы - есть у роли студента
    private String groupName;
    // Кафедра
    private String department;
    private String faculty;
    // academicTitle и academicDegree - если преподаватель
    private String academicTitle;
    private String academicDegree;
    // Заблокирован ли аккаунт - в случаен отчисления или увольнения
    private Boolean isBlocked;

    private String email;
    private String status;
} 

