package com.aait.aaitims.Entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "theCourse")
public class Courses {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String titlee;
  private String creditHourr;
  private String ECTSs;
  

 
}
