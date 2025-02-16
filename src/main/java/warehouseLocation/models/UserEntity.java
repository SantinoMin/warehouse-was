//package warehouseLocation.models;
//
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@Table(name = "user")
//public class UserEntity {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "user_id", nullable = false, unique = true)
//  private int userId;
//
//  @Column(name = "user_name", nullable = false)
//  private String userName;
//
//  @Column(name = "email", nullable = false)
//  private String email;
//
//  @Column(name = "password", nullable = false)
//  private String password;
//
//  @Column(name = "birthday", nullable = false)
//  private LocalDate birthday;
//
//  @Column(name = "created_at", nullable = false)
//  private LocalDateTime createdAt;
//
//  @Column(name = "updated_at", nullable = false)
//  private LocalDateTime updatedAt;
//
//  @Column(name = "deleted_at", nullable = false)
//  private LocalDateTime deletedAt;
//
//  @Column(name = "status", nullable = false)
//  private String status;
//
//  @Column(name = "role", nullable = false)
//  private String role;
//  };
