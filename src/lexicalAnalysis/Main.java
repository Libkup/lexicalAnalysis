package lexicalAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @Desc �ʷ�������������ΪԴ�ļ������Ϊtoken���кͱ�Ҫ�ı�����Ϣ
 * @author LiBenkang
 * @Date 2019��5��8��
 */
public class Main {

	static int state = 0; 						//��¼״̬����״̬
	static char c;							//��ǰ��ȡ���ַ�
	static char[] chs = new char[0];
	static int position = 0;
	static BufferedReader reader = null;
	static BufferedWriter writer = null;
	static String line;
	static String currentToken = "";
	static boolean flag = true;
	static boolean firstLine = false;
	static int lineNumber = 0;
	
	// �̱߳����ؼ���
	static String[] keyWords = {"thread", "features",  "flows",  "properties","end","none",  
								"in",  "out",  "data",  "port",  "event",  "parameter",  "flow" ,  
								"source", "sink" ,  "path", "constant" ,  "access"};
	
	// �̵߳ĺϷ����źͶ�Ӧ�ķ��ŵ���
	static String[] symbols = {"=>", "+=>" ,";" , ":", "::", "{", "}", "->"}; 
	static String[] symbolsLetter = {"EQUALGREATER", "PLUSEQUALGREATER" ,"SEMICOLON" , "COLON",
									 "DOUBLECOLON", "LEFTBRACKETS", "RIGHTBRACKETS", "MINUSGREATER"}; 
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {

		reader = new BufferedReader(new FileReader(new File("test3.txt")));;
		writer = new BufferedWriter(new FileWriter(new File("tokenOut3.txt")));
		while(state != 13){
			if(flag)
				c = getChar();
			else
				flag = true;
			if(c == '$'){
				writer.newLine();
				writer.write("EOF");
				writer.flush();
				break;
			}
			switch(state){
			case 0:
				if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
					state = 1;
					currentToken = currentToken + c;
				}
				else if(c == '+'){
					state = 4;
					currentToken = currentToken + c;
				}
				else if(c >= '0' && c <= '9'){
					state = 5;
					currentToken = currentToken + c;
				}
				else if(c == '-'){
					state = 8;
					currentToken = currentToken + c;
				}
				else if(c == '='){
					state = 9;
					currentToken = currentToken + c;
				}
				else if(c == ';' || c == '{' || c == '}'){
					state = 10;
					currentToken = currentToken + c;
				}	
				else if(c == ':'){
					state = 11;
					currentToken = currentToken + c;
				}
				else if(c == ' ' || c == '	')
					state = 0;
				else {
					state = 12;
					flag = false;
					currentToken = "";
				}
					
				break;
			case 1:
				if(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
					state = 2;
					currentToken = currentToken + c;
				}
				else if(c == '_'){
					state = 3;
					currentToken = currentToken + c;
				}
				else {
					state = 0;
					if(isKeyWords(currentToken).equals("none")){
						writer.write("IDENTIFIER ");
						writer.flush();
					}else{
						writer.write(isKeyWords(currentToken).toUpperCase() + " ");
						writer.flush();
					}
					flag = false;
					currentToken = "";
				}
//				else {
//					state = 0;
//					writer.write("IDENTIFIER ");
//					writer.flush();
//				}
					
				break;
			case 2:
				if(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
					state = 2;
					currentToken = currentToken + c;
				}
				else if(c == '_'){
					state = 3;
					currentToken = currentToken + c;
				}
				else {
					state = 0;
					if(isKeyWords(currentToken).equals("none")){
						writer.write("IDENTIFIER ");
						writer.flush();
					}else{
						writer.write(isKeyWords(currentToken).toUpperCase() +" ");
						writer.flush();
					}
					flag = false;
					currentToken = "";
				}
//				else
//					state = 12;
				break;
			case 3:
				if(c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
					state = 2;
					currentToken = currentToken + c;
				}
				else {
					state = 0;
					flag = false;
					currentToken = "";
				}
					
				break;
			case 4:
				if(c >= '0' && c <= '9'){
					state = 5;
					currentToken = currentToken + c;
				}
				else if(c == '='){
					state = 9;
					currentToken = currentToken + c;
				}
				else{
					state = 0;
					flag = false;
					currentToken = "";
				}
				break;
			case 5:
				if(c >= '0' && c <= '9'){
					state = 5;
					currentToken = currentToken + c;
				}
				else if(c == '.'){
					state = 6;
					currentToken = currentToken + c;
				}
				else{
					state = 0;
					flag = false;
					currentToken = "";
				}
				break;
			case 6:
				if(c >= '0' && c <= '9'){
					state = 7;
					currentToken = currentToken + c;
				}
				else{
					state = 0;
					flag = false;
					currentToken = "";
				}
				break;
			case 7:
				if(c >= '0' && c <= '9'){
					state = 7;
					currentToken = currentToken + c;
				}	
				else{
					state = 0;
					if(isKeyWords(currentToken).equals("none")){
						writer.write("DECIMAL ");
						writer.flush();
					}else{
						writer.write(isKeyWords(currentToken) + " ");
						writer.flush();
					}
					flag = false;
					currentToken = "";
				}
				break;
			case 8:
				if(c >= '0' && c <= '9'){
					state = 5;
					currentToken = currentToken + c;
				}	
				else if(c == '>'){
					state = 10;
					currentToken = currentToken + c;
				}	
				else{
					state = 0;
					flag = false;
					currentToken = "";
				}
				break;
			case 9:
				if(c == '>'){
					state = 10;
					currentToken = currentToken + c;
				}
				else{
					state = 0;
					flag = false;
					currentToken = "";
				}
				break;
			case 10:
				state = 0;
				flag = false;
				if(isSymbol(currentToken).equals("none")){
					writer.write("error ");
					writer.flush();
				}else{
					writer.write(isSymbol(currentToken) + " ");
					writer.flush();
				}
				currentToken = "";
				break;
			case 11:
				if(c == ':'){
					state = 10;
					currentToken = currentToken + c;
				}
				else {
					state = 0;
					if(isSymbol(currentToken).equals("none")){
						writer.write("error ");
						writer.flush();
					}else{
						writer.write(isSymbol(currentToken) + " ");
						writer.flush();
					}
					currentToken = "";
				}	
				break;
			case 12:
				state = 0;
				
				
				if(c == '.'){
					c = getChar();
					String str = "";
					while(c >= '0' && c <= '9'){
						str += c;
						c = getChar();
					}
						
					writer.write("(error: ." + str + " is not a legal decimal, line " + lineNumber + ")");
					writer.flush();
				}else{
					writer.write("(error: " + c + " is not a legal letter here, line " + lineNumber + ")");
					writer.flush();
				}
				currentToken = "";
				break;
			}
		}
		
	}

	/**
	 * 
	 * @Desc ��ȡԴ�ļ�����һ�ַ�
	 * @author LiBenkang
	 * @Date 2019��5��8��
	 * @return char
	 */
	public static char getChar() throws IOException {
		if(position == chs.length)
			chs = new char[0];
		if(chs.length == 0){
			position = 0;
			line = reader.readLine();
			lineNumber ++ ;
			if(line == null)
				return '$';
			if(line.equals("")){
				line = reader.readLine();
				lineNumber ++;
			}
				
			line += ' ';
			chs = line.toCharArray();
			if(firstLine)
				writer.newLine();
			else{
				firstLine = true;
				writer.write("EOF");
				writer.newLine();
				writer.flush();
			}
				
		}
		++ position;
//		System.out.println(chs[position-1]);
		return chs[position-1];
	}
	
	/**
	 * 
	 * @Desc �жϵ�ǰ�ַ����Ƿ�Ϊ�̱߳����Ĺؼ���
	 * @author LiBenkang
	 * @Date 2019��5��8��
	 * @return String
	 */
	public static String isKeyWords(String currToken){
		for(int i = 0; i < keyWords.length; i++){
			if(currToken.equals(keyWords[i]))
				return keyWords[i];
		}
		return "none";
	}
	
	/**
	 * 
	 * @Desc �жϵ�ǰ�ַ����Ƿ�Ϊ�̵߳ĺϷ�����
	 * @author LiBenkang
	 * @Date 2019��5��8��
	 * @return String
	 */
	public static String isSymbol(String symbol){
		for(int i = 0; i < symbols.length; i++){
			if(symbol.equals(symbols[i]))
				return symbolsLetter[i];
		}
		return "none";
	}
	
}
