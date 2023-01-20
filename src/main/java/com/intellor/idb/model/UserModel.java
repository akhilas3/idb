package com.intellor.idb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

//  @ManyToOne
//  @JoinColumn(name = "role_id")
//  private Role role;

}
