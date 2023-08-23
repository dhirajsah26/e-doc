package com.example.edoc.util;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
Initialize web bindings
*/
public class MyAppBindingInitializer implements WebBindingInitializer {

  /*
    Date pattern applied to all the web app dates
   */
  public static final String DATE_PATTERN = "yyyy-MM-dd";

  public void initBinder(WebDataBinder binder, WebRequest request) {
      // Date editor with pattern
      SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
      dateFormat.setLenient(false);
      binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

      // Trim Strings
      binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
      
      // Number editors
      binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
      binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));

  }

}
