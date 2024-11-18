package com.petshop.core.utils;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public class CustomDatePicker {

    private JDatePickerImpl datePicker;
    private static final Color TOP_PANEL_COLOR = new Color(30, 10, 60);

    public CustomDatePicker() {
        UtilDateModel model = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Hoje");
        p.put("text.month", "Mês");
        p.put("text.year", "Ano");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JButton calendarButton = (JButton) datePicker.getComponent(1);

        calendarButton.setBackground(TOP_PANEL_COLOR);
        calendarButton.setForeground(Color.WHITE);
        calendarButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        calendarButton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        calendarButton.setPreferredSize(new Dimension(25, 25));
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }

    public String getSelectedDate() {
        return datePicker.getJFormattedTextField().getText().trim();
    }

    static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        @Override
        public Object stringToValue(String text) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                return formatter.format(cal.getTime());
            }
            return "";
        }
    }
}
