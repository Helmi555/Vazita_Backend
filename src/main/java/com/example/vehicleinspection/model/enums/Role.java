package com.example.vehicleinspection.model.enums;

public enum Role {
     ROLE_ADMIN, ROLE_INSPECTOR, ROLE_ADJOINT;

     public static Role fromCode(int code) {
          return switch (code) {
               case 1 -> ROLE_ADMIN;
               case 2 -> ROLE_INSPECTOR;
               case 3 -> ROLE_ADJOINT;
               default -> throw new IllegalArgumentException("Invalid role code: " + code);
          };
     }
}