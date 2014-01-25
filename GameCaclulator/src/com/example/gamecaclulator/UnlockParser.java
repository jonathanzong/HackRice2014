import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class UnlockParser {

	private static HashMap<String,String> regexes = new HashMap<String,String>();
	private static HashMap<String,String> anses = new HashMap<String,String>();

	private static ScriptEngine engine = 
            new ScriptEngineManager().getEngineByName("javascript");
	
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
		anses.put("0", "0"); // digits
		anses.put("2", "2"); // 
		anses.put("3", "3"); // 
		anses.put("4", "4"); // 
		anses.put("5", "5"); // 
		anses.put("6", "6"); // 
		anses.put("7", "7"); // 
		anses.put("8", "8"); // 
		anses.put("9", "9"); //
		
		try {
			engine.eval("function sFact(num){ var rval=1; for (var i = 2; i <= num; i++) rval = rval * i; return rval;}");
		} catch (ScriptException e) {
			// freak out and die
			e.printStackTrace();
		}
	}
	
	public static Set<String> checkAns(String ans){
		Set<String> set = new HashSet<String>();
		for(String key: anses.keySet()){
			if(ans.matches(key))
				set.add(anses.get(key));
		}
//		anses.values().removeAll(set);
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
//		regexes.values().removeAll(set);
		return set;
	}
	
	private static String pival = Math.PI+"";
	private static String tauval = 2*Math.PI+""; 
	private static String eulerval = Math.E+"";
	
	// returns null if there's a problem, otherwise returns answer
	public static Double mathEval(String expr){
		
		try {
			expr = expr.replace("\u03C0", pival)
					.replace("e", eulerval)
					.replace("\u03C4", tauval)
					.replace("x", "*")
					.replace("sin","Math.sin")
					.replace("cos", "Math.cos")
					.replace("tan", "Math.tan")
					.replace("\u221A", "Math.sqrt")
					.replaceAll("(\\d+)\\^(\\d+)", "Math.pow($1,$2)")
					.replaceAll("(\\d+)\\!", "sFact($1)");

			Object o = engine.eval(expr);
			return (Double)o;
		} catch (ScriptException e) {
			return null;
		}
	}
	
	//test
	public static void main(String[] args){
		System.out.println(parseExpr("1+1+1"));
		System.out.println(parseExpr("1+1+1"));
		System.out.println(parseExpr("1x1x1"));
		System.out.println(parseExpr("312^-13"));
		System.out.println(parseExpr("9x8x7x6x5x4x3x2x1"));
		System.out.println(parseExpr("4^(1/2)"));
		System.out.println(parseExpr("4/(4^2+5^2)"));
		System.out.println(parseExpr("4/(5^2+4^2)"));
		System.out.println(checkAns("3.14159"));
		System.out.println(checkAns("2.71828"));
		System.out.println(checkAns("6.28318"));
		System.out.println(checkAns("6"));

		System.out.println(mathEval("6^2"));
		System.out.println(mathEval("sin(\u03C4/4)"));
		System.out.println(mathEval("4!+1"));
		
		// if expr has / and ans has ., unlock %
	}

}
