package com.gova.EasyGuide.entities.bd1;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users_table")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class User extends BaseUser {
}
