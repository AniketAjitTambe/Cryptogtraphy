
import java.util.*;
import java.math.*;

class rsa
{
	public static int e_key, d_key, n_key;
	//==================================================================================================================//
	//SYMMETRIC KEY ENCRYPTION START
	//==================================================================================================================//
	//Key generation for Symmetric Key Encryption
	public int get_secretKey()
	{
		Random rand = new Random();
		return rand.nextInt(100);
	}
	
	//==================================================================================================================//
	//Symmetric Key Encryption : Encrypt string str1 with key n2
	public String encrypt_now(String str1 , int n2)
	{
		String dec = "" , str = str1;
		int n = n2 , c;
		int length = str.length();
		char ch;
		char ch_array[] = new char[length];
		
		for(int i = 0 ; i<length ; i++)
		{
			c = str.charAt(i)+n2;
			ch = (char)(c+n);
			ch_array[i] = (char)c;
		}
		String cipher = String.valueOf(ch_array);
		return cipher;
		//System.out.println("Cipher : "+cipher);
	}
	
	//==================================================================================================================//
	//Symmetric Key Encryption : Decrypt cipher str with key n2
	public String decrypt_now(String str , int n)
	{
		int c;
		int length = str.length();
		char ch;
		char ch_array[] = new char[length];
		char dec[] = new char[length];
		ch_array = str.toCharArray();
		
		for(int i = 0 ; i<length ; i++)
		{
			c = ch_array[i];
			c = c-n;
			dec[i] = (char)c;
		}
		String decrypt = String.valueOf(dec);
		return decrypt;
		//System.out.println("Decrypt : "+decrypt);
	}

	//==================================================================================================================//
	//SYMMETRIC KEY ENCRYPTION END
	//==================================================================================================================//
	
	//==================================================================================================================//
	//ASYMMETRIC KEY ENCRYPTION START
	//==================================================================================================================//
	//Function to generate e,d,n keys
	//public static void generate_key()
	public static int[] generate_key()
	{
		int p, q, n, z, d = 0, e, i;
		int[] p_arr = new int[] {313,269,317};
		int[] q_arr = new int[] {239,281,337};
		p = p_arr[new Random().nextInt(p_arr.length)];  //First Prime Number
		q = q_arr[new Random().nextInt(q_arr.length)]; //Second Prime Number
			
		n = p * q;
		n_key = n;
        z = (p - 1) * (q - 1);
        //System.out.println("the value of z = " + z);
  
        for (e = 2; e < z; e++) {
  
            // e is for public key exponent
            if (gcd(e, z) == 1) {
				e_key = e;
                break;
            }
        }
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * z);
  
            // d is for private key exponent
            if (x % e == 0) {
                d = x / e;
				d_key = d;
                break;
            }
        }
		
		//===Test===========================================================================================================//		
		//System.out.println("e_key : "+e_key+"\nd_key : "+d_key+"\nn_key : "+n_key);
		//System.out.println("Generator Function is rsa");
		//System.out.println("Keys are : "+e_key+" "+d_key+" "+n_key);
		//==================================================================================================================//
		
		int a[] = {e_key , d_key , n_key};
		return a;

	}
	
	static int gcd(int e , int z)
	{
		if(e == 0)
			return z;
		else
			return gcd(z % e , e);
	}
	
	//public static double encrypt_msg(int msg)
	public static String encrypt_msg(int msg , int e_key , int n_key)
	{
		double c;
		//System.out.println("\n\nEncrypting using :\n\te_key : "+e_key+"\n\tn_key : "+n_key);
		c = (Math.pow(msg, e_key)) % n_key;
		//return c;
		String s=String.valueOf(c);  
		return s;
		
		//Returns Double type
		
	}
	
	//public static BigInteger decrypt_msg(double msg)
	public static int decrypt_msg(String s , int d_key , int n_key)
	{
		double msg = Double.parseDouble(s);
		BigInteger msgback;
		// converting int value of n to BigInteger
        BigInteger N = BigInteger.valueOf(n_key);
  
        // converting float value of c to BigInteger
        BigInteger C = BigDecimal.valueOf(msg).toBigInteger();
        msgback = (C.pow(d_key)).mod(N);
        //System.out.println("Derypted message is : ");
		int decipher = msgback.intValue();
        //return msgback;
		//Returns BigInteger
		return decipher;
	}
	
	//==================================================================================================================//
	//ASYMMETRIC KEY ENCRYPTION END
	//==================================================================================================================//
	
	//==================================================================================================================//
	//TEST PURPOSE
	//==================================================================================================================//
	// remove n from nmain to run main function
	public static void nmain(String[] args)
	{
		//int keys[] = new int[3];
		//keys = generate_key();
		try{
			Scanner sc=new Scanner(System.in);
			System.out.print("Enter the number to be encrypted and decrypted : ");
			int msg=sc.nextInt();
			generate_key();
			System.out.println("Message is : "+ msg);
					
			//double c = encrypt_msg(msg);
			String c = encrypt_msg(msg,e_key,n_key);
			System.out.println("Encrypted message is : " + c);
			
			//BigInteger c1 = decrypt_msg(c);
			int c1 = decrypt_msg(c,d_key,n_key);
			System.out.println("Derypted message is : "+ c1);
			
		}catch(Exception e){}
	}
	
}