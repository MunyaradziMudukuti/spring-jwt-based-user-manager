package zw.co.munyasys.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasicFormats {
    public static final String DATE = "dd/MM/yyyy";
    public static final String TIME = "HH:mm";
    public static final String FULL_DATE_TIME = DATE + " " + TIME;

}