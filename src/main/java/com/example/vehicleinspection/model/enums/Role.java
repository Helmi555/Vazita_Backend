package com.example.vehicleinspection.model.enums;

public enum Role {
     ADMIN, INSPECTEUR, ADJOINT;

     public static Role fromCode(String code) {
          return switch (code) {
               case "001" -> ADMIN;
               case "002" -> INSPECTEUR;
               case "003" -> ADJOINT;
               default -> throw new IllegalArgumentException("Invalid role code: " + code);
          };
     }

     public static String fromRole(Role role) {
          return switch (role) {
               case ADMIN ->"001";
               case INSPECTEUR-> "002";
               case ADJOINT->"003";
               default -> throw new IllegalArgumentException("Invalid role : " + role);
          };
     }

     public static boolean isValid(String roleName) {
          if (roleName == null) {
               return false;
          }
          try {
               Role.valueOf(roleName);
               return true;
          } catch (IllegalArgumentException e) {
               return false;
          }
     }
}