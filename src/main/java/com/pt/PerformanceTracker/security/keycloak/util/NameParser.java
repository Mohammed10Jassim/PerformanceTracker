package com.pt.PerformanceTracker.security.keycloak.util;

import lombok.Generated;

public final class NameParser {
   public static String getFirstName(String name) {
      if (name != null && !name.isBlank()) {
         String cleanedName = name.trim();
         int spaceIndex = cleanedName.indexOf(" ");
         return spaceIndex == -1 ? cleanedName : cleanedName.substring(0, spaceIndex).trim();
      } else {
         return null;
      }
   }

   public static String getLastName(String name) {
      if (name != null && !name.isBlank()) {
         String cleanedName = name.trim();
         int spaceIndex = cleanedName.indexOf(" ");
         if (spaceIndex == -1) {
            return null;
         } else {
            String lastName = cleanedName.substring(spaceIndex + 1).trim();
            return lastName.isBlank() ? null : lastName;
         }
      } else {
         return null;
      }
   }

   @Generated
   private NameParser() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
