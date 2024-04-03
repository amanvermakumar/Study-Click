package com.example.Project.Entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.reflect.GenericDeclaration;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sid;

    @NotBlank(message = "Username can not be null")
    @Size(min = 3,max = 12 ,message = "Username must be between 3-12 characters")
    public String name;

    @Column(unique = true)
    @NotBlank(message = "Email can not be null")
    public String email;
    @NotBlank(message = "Password can not be null")
    @Size(min = 3, message = "Password must be between 3-13 characters")
    public String password;
    @NotBlank(message = "Year can not be null")
    private String year;
    @NotBlank(message = "Branch can not be null")
    public String branch;
    @NotBlank(message = "RollNo can not be null")
    @Size(max = 15, message = "RollNo less than or equal to 15  digits")
    public String rollNo;
    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private List<Result> results;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String role;
    @NotBlank(message = "college can not be null")
    public String college;
    @NotBlank(message = "PhoneNo can not be null")
    @Size(max = 10, message = "PhoneNo less than or equal to 10 digits")
    public String phone;
    @NotBlank(message = "Gender can not be null")
    public String gender;
    @NotBlank(message = "DOB can not be null")
    public String dob;
    public String image;
    @Lob
    public byte [] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Student() {
        super();
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", branch='" + branch + '\'' +
                ", rollNo='" + rollNo + '\'' +
                ", role='" + role + '\'' +
                ", college='" + college + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
