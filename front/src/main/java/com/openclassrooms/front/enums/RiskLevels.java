package com.openclassrooms.front.enums;

public enum RiskLevels {
     NONE, BORDERLINE, IN_DANGER, EARLY_ONSET;

     public String displayStatus() {
         return switch (this) {
             case NONE -> "Aucun risque";
             case BORDERLINE -> "Risque limité";
             case IN_DANGER -> "Danger";
             case EARLY_ONSET -> "Apparition précoce";
         };
     }
}
