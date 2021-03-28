package br.com.lgs.accounting.core.common.validation;

import br.com.lgs.accounting.core.common.validation.annotations.ValidateCPF;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateNotEmpty;
import br.com.lgs.accounting.core.common.validation.annotations.ValidateNotNull;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

public class Validator {

    public static <T> Set<ConstraintViolation<T>> validate(T objeto) {
        Class<?> classe = objeto.getClass();
        Set<ConstraintViolation<T>> violations = new HashSet<>();
        for (Field field : classe.getDeclaredFields()) {
            if (field.isAnnotationPresent(ValidateNotEmpty.class)) {
                try{
                    field.setAccessible(true);
                    String value = (String) field.get(objeto);
                    if(isEmpty(value)) {
                        violations.add(new ConstraintViolation<>(field.getName(), "Campo obrigatório"));
                    }
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (field.isAnnotationPresent(ValidateNotNull.class)) {
                try{
                    field.setAccessible(true);
                    Object value = field.get(objeto);
                    if(isNull(value)) {
                        violations.add(new ConstraintViolation<>(field.getName(), "Campo obrigatório"));
                    }
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if(field.isAnnotationPresent(ValidateCPF.class)) {
                try{
                    field.setAccessible(true);
                    String value = (String) field.get(objeto);
                    if(isInvalidCPF(value)) {
                        violations.add(new ConstraintViolation<>(field.getName(), "CPF inválido"));
                    }
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return violations;
    }

    private static boolean isNull(Object value) {
        return value == null;
    }

    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private static boolean isInvalidCPF(String CPF) {
        if (CPF == null ||
                CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return true;
        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            r = (sm * 10) % 11;
            if (r == 10)
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }

            r = (sm * 10) % 11;
            if (r == 10)
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if (!((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))))
                return true;
        } catch (InputMismatchException ex) {
            return true;
        }
        return false;
    }
}