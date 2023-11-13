package com.mymortgagecalculator;

import java.text.NumberFormat;
import java.util.Locale;

/*
M = P[r(1 + r)^n] / [(1 + r)^n - 1]

M = mortgage value;
P or Principal: the amount the user is going to loan;
r = monthly interest rate;
n = number of payments by months.
*/

/*
User´s inputs:
Principal: (R$1k - R$1M);
Annual Interest Rate(>0 and <= 30):  given in percentage (So, I will need to divide this user´s input by 100, and then divide it by 12, to get the actual monthly interest rate);
Period (Years) (value between 1 and 30):  (So, I will need to multiply this by 12, to get the actual number of payments by months).

Desired Output:
Mortgage: (monetary value formatted in system´s currency).
*/

    /**
     * This class represents a mortgage calculator that calculates
     * monthly payments and payment schedules based on user inputs.
     */
    public class MortgageCalculator {
        private final Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        private final byte MONTHS_IN_YEAR = 12;
        private int principal;
        private float monthlyInterestRate;
        private int paymentMonths;
        private double monthlyPayment;

        /**
         * Prompts the user for the principal amount and stores it in the 'principal' field.
         * The method ensures that the entered value is within the specified range.
         */
        public void setPrincipal() {
            final int MIN_LOAN_AMOUNT = 1_000;
            final int MAX_LOAN_AMOUNT = 1_000_000;

            principal = InputValidator.getValidInteger("Principal($1k-1M): ", MIN_LOAN_AMOUNT, MAX_LOAN_AMOUNT);

        }

        /**
         * Prompts the user for the annual interest rate and converts it to a monthly rate,
         * which is stored in 'monthlyInterestRate'.
         * The method ensures that the entered value is within the specified range.
         */
        public void setMonthlyInterestRate() {
            final int MIN_ANNUAL_INTEREST_RATE = 0;
            final int MAX_ANNUAL_INTEREST_RATE = 30;
            final byte percent = 100;
            float annualInterestRate;

            annualInterestRate = InputValidator.getValidFloat("Annual Interest Rate(%): ", MIN_ANNUAL_INTEREST_RATE, MAX_ANNUAL_INTEREST_RATE);
            monthlyInterestRate = (annualInterestRate / percent) / MONTHS_IN_YEAR;
        }

        /**
         * Prompts the user for the loan period in years and converts it to the total number
         * of payments in months, stored in 'paymentMonths'.
         * The method ensures that the entered value is within the specified range.
         */
        public void setPeriodYears() {
            byte years;
            final byte MIN_YEARS = 1;
            final byte MAX_YEARS = 30;

            years = (byte) InputValidator.getValidInteger("Period(Years): ", MIN_YEARS, MAX_YEARS);
            paymentMonths = (years * MONTHS_IN_YEAR);
        }

        /**
         * Calculates the mortgage value based on the principal, monthly interest rate, and
         * total number of payments. The result is stored in 'monthlyPayment'.
         */
        private double calculateMonthlyPayment() {
            monthlyPayment = (principal
                    * monthlyInterestRate * Math.pow((1 + monthlyInterestRate), paymentMonths))
                    / (Math.pow((1 + monthlyInterestRate), paymentMonths) - 1);

            return monthlyPayment;
        }

        public void printMonthlyPayment() {
            System.out.println(" ");
            System.out.println("MORTGAGE");
            System.out.println("------------------------------------------------------------");
            System.out.println("Monthly Payment = " + currencyFormat.format(monthlyPayment));
        }

        /**
         * Calculates and prints the payment schedule, including remaining balances, for each month.
         */
        public void calculateAndPrintPaymentSchedule() {
            if (monthlyPayment <= 0.0) {
                System.out.println("The loan will not be paid with the provided conditions.");
                return;
            }

            double remainingBalance = principal;
            double epsilon = 10e-6;

            System.out.println(" ");
            System.out.println("PAYMENT SCHEDULE");
            System.out.println("------------------------------------------------------------");

            for (int month = 1; month <= paymentMonths && remainingBalance >= epsilon; month++) {
                double interestPayment = remainingBalance * monthlyInterestRate;
                double principalPayment = monthlyPayment - interestPayment;

                remainingBalance -= principalPayment;

                if (remainingBalance <= principalPayment || Math.abs(remainingBalance) < epsilon) {
                    remainingBalance = 0.0;
                }

                System.out.println(month + "\t" + currencyFormat.format(remainingBalance));
            }
        }

        public void calculateMortgage() {
            setPrincipal();
            setMonthlyInterestRate();
            setPeriodYears();
            calculateMonthlyPayment();
            printMonthlyPayment();
            calculateAndPrintPaymentSchedule();
        }
    }


