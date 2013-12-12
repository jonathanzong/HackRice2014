package com.example.gamecaclulator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomOperator;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class UnlockParser2 {

        private static HashMap<String,String> regexes = new HashMap<String,String>();
        private static HashMap<String,String> anses = new HashMap<String,String>();

        static{
                regexes.put("((\\d+)\\+(\\2))(\\+\\2)*", "x"); // mult
                regexes.put("((\\d+)x(\\2))(x\\2)*", "^"); // exp
                regexes.put("(\\d+)\\^\\-\\d+", "/"); // div
                regexes.put("(((((((9x)?8x)?7x)?6x)?5x)?4x)?)?3x2x1", "!"); // fact
                regexes.put("\\d+\\^\\(1\\/2\\)", "\u221A"); // sqrt
                regexes.put("(\\d+)\\/((\\(\\1\\^2\\+\\d+\\^2\\))|(\\(\\d+\\^2\\+\\1\\^2\\)))", "trig"); // sin, do the rest of the trig options later
                anses.put("\\d+\\.\\d+", "."); // decimal
                anses.put("3\\.14159", "\u03C0"); // pi
                anses.put("2\\.71828", "e"); // e
                anses.put("6\\.28318", "\u03C4"); // tau
                anses.put("0(\\.0)?", "0"); // digits
                anses.put("2(\\.0)?", "2"); // 
                anses.put("3(\\.0)?", "3"); // 
                anses.put("4(\\.0)?", "4"); // 
                anses.put("5(\\.0)?", "5"); // 
                anses.put("6(\\.0)?", "6"); // 
                anses.put("7(\\.0)?", "7"); // 
                anses.put("8(\\.0)?", "8"); // 
                anses.put("9(\\.0)?", "9"); //

                //
        }

        public static Set<String> checkAns(String ans){
                Set<String> set = new HashSet<String>();
                for(String key: anses.keySet()){
                        if(ans.matches(key))
                                set.add(anses.get(key));
                }
                //                anses.values().removeAll(set);
                return set;
        }

        // returns newly unlocked symbols. each symbol is only returned once per runtime so stick them
        // in SharedPreferences when you receive them
        public static Set<String> parseExpr(String calc){
                Set<String> set = new HashSet<String>();
                for(String reg: regexes.keySet()){
                        if(calc.matches(reg))
                                set.add(regexes.get(reg));
                }
                //                regexes.values().removeAll(set);
                return set;
        }

        private static String pival = Math.PI+"";
        private static String tauval = 2*Math.PI+""; 
        private static String eulerval = Math.E+"";

        private static CustomOperator factorial = new CustomOperator("!", true, 6, 1){
                @Override
                protected double applyOperation(double[] values) {
                        double tmp = 1d;
                        int steps = 1;
                        while (steps < values[0]) {
                                tmp = tmp * (++steps);
                        }
                        return tmp;
                }
        };

        public static Double mathEval(String expr) throws UnknownFunctionException, UnparsableExpressionException{
                
                expr = expr.replace("\u03C0", pival)
                                .replace("e", eulerval)
                                .replace("\u03C4", tauval)
                                .replace("x", "*")
                                .replaceAll("\u221A(\\d+)", "sqrt($1)")
                                .replaceAll("(\\d+)\\!", "($1!)");

                Calculable calc = new ExpressionBuilder(expr)
                        .withOperation(factorial)
                                .build();
                double result1=calc.calculate();
                return result1;
        }

}
