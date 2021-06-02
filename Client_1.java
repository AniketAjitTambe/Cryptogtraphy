import java.io.*;
import java.net.*;
import java.util.*;

public class Client_1 extends rsa
{
	final static int ServerPort = 1234;
	
	//Important variable
	public static int sec_key=11;
	private static int e_key_my = 0 , d_key_my = 0 , n_key_my = 0 , e_key_reciever = 0 , n_key_reciever = 0 , my_sec_key = 0 , recieved_sec_key = 0;
	public static boolean send_my_key = false , recieved_reciever_key = false ,send_secret_key = false ,recieved_secret_key = false;
	
	//==================================================================================================================//
	private static void show_keys()
	{
		//Sender's RSA Keys
		System.out.println("\n===FINAL KEYS FOR CRYPTOGRAPHY=============================================== \n");
		
		System.out.println("\n* * * * * ASYMMETRIC KEY ENCRYPTION * * * * * \n");
		
		System.out.println("Client 1 RSA Keys are : Private Key :("+d_key_my+","+n_key_my+")");
		System.out.println("Client 1 RSA Keys are : Public  Key :("+e_key_my+","+n_key_my+") (SENT)");
		
		//Reciever's RSA Keys
		System.out.println("\nClient 0 RSA Keys are : Public  Key :("+e_key_reciever+","+n_key_reciever+") (RECIEVED)");
		
		System.out.println("\n* * * * * SYMMETRIC KEY ENCRYPTION * * * * * \n");

		//Final Secret Key for Message Encryption/Decryption
		System.out.println("Sender's Half of Secret Key         : "+my_sec_key);
		System.out.println("Receiver's Half of Secret Key       : "+recieved_sec_key);
		System.out.println("\nSecret Key for Message transfer     : "+sec_key);
		System.out.println("\n============================================================================== \n");
	}
	
	private static boolean check_keys()
	{
		if((send_my_key==true)&&(send_secret_key==true)&&(recieved_reciever_key==true)&&(recieved_secret_key==true))
			return true;
		else
			return false;
	}
	
	//==================================================================================================================//
    public static void main(String args[]) throws UnknownHostException, IOException
	{
		//===IMPORTANT DECLARATION=================================================================================================//
		//For input
		Scanner scn = new Scanner(System.in);
          
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");
          
        // establish the connection
        Socket s = new Socket(ip, ServerPort);
          
        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		System.out.println("Press Enter to continue!");
		scn.nextLine();
		
		//===KEY EXCHANGE  ALGORITHM===========================================================================================//
		//Generate RSA and Secret Keys
		rsa r = new rsa();
		sec_key = r.get_secretKey();
		my_sec_key = sec_key;
		System.out.println("Sender's Half of Secret Key : "+sec_key);
		int[] keys = r.generate_key();
		e_key_my = keys[0];
		d_key_my = keys[1];
		n_key_my = keys[2];
		//show_keys();
		
		//===THREAD TO SEND KEY===============================================================================================//
		
		Thread sendKeyThread = new Thread(new Runnable() 
        {
            @Override
            public void run() {
					try{
						
						if(send_my_key == false)
						{
							String msg = "$K-:"+e_key_my+":"+n_key_my+":";
							System.out.println("Sending Client 1's Public key : "+msg);
							System.out.println("Client 1's Public key sent");
							send_my_key = true;
							dos.writeUTF(msg+"#client 0");						
						}
																				
						if(send_secret_key == false && recieved_reciever_key == true)
						{
							//sec_key = r.get_secretKey();
							String msg = "$S-:"+r.encrypt_msg(sec_key,e_key_reciever,n_key_reciever);
							send_secret_key = true;
							System.out.println("Client 1's Secret Key sent (Encrypted with Reciever's public Key) : "+msg);
							dos.writeUTF(msg+"#client 0");	
						}
						
										
						
						
					}catch(Exception e){}
            }
        });
		
		//===THREAD TO RECIEVE KEY============================================================================================//
		
		Thread recieveKeyThread = new Thread(new Runnable() 
        {
            @Override
            public void run() {
				try{
					
						String msg = dis.readUTF();
						
						if(msg.substring(0,4).equals("$K-:"))
						{
							StringTokenizer st = new StringTokenizer(msg, ":");
							String x1 = st.nextToken();
							e_key_reciever = Integer.valueOf(st.nextToken());
							n_key_reciever = Integer.valueOf(st.nextToken());
							
							System.out.println("\nClient 0's Public Keys recieved : "+msg);
							recieved_reciever_key = true;

							//sendMessage.resume();
						}
						
						if(msg.substring(0,4).equals("$S-:"))
						{
							StringTokenizer st = new StringTokenizer(msg, ":");
							String x1 = st.nextToken();
							int sec_key_2 = r.decrypt_msg(st.nextToken(),d_key_my,n_key_my);
							sec_key = sec_key + sec_key_2;
							recieved_sec_key = sec_key_2;
							System.out.println("Client 0's Secret Keys Recieved (Encrypted) : "+msg);
							
							//System.out.println("Updated Security Key : "+sec_key);
							recieved_secret_key =true;
							//sendMessage.resume();
						}
												
						
						
					}catch(Exception e){}                   
            }
        });
		
		//===THREAD TO SEND SECRET KEY===============================================================================================//
		
		Thread sendSecretKeyThread = new Thread(new Runnable() 
        {
            @Override
            public void run() {
					try{
																								
						if(send_secret_key == false && recieved_reciever_key == true)
						{
							//sec_key = r.get_secretKey();
							String msg = "$S-:"+r.encrypt_msg(sec_key,e_key_reciever,n_key_reciever);
							send_secret_key = true;
							System.out.println("Client 1's Secret Key sent (Encrypted with Reciever's public Key) : "+msg);
							dos.writeUTF(msg+"#client 0");	
						}
						
						
					}catch(Exception e){}
            }
        });
		
		//===THREAD TO RECIEVE SECRET KEY============================================================================================//
		Thread recieveSecretKeyThread = new Thread(new Runnable() 
        {
            @Override
            public void run() {
				try{
					
						String msg = dis.readUTF();
												
						if(msg.substring(0,4).equals("$S-:"))
						{
							StringTokenizer st = new StringTokenizer(msg, ":");
							String x1 = st.nextToken();
							int sec_key_2 = r.decrypt_msg(st.nextToken(),d_key_my,n_key_my);
							sec_key = sec_key + sec_key_2;
							recieved_sec_key = sec_key_2;
							System.out.println("Client 0's Secret Keys Recieved (Encrypted) : "+msg);
							
							//System.out.println("Updated Security Key : "+sec_key);
							recieved_secret_key =true;
							//sendMessage.resume();
						}
												
						
					}catch(Exception e){}                   
            }
        });
		
		//===THREAD TO SEND MESSAGE===================================================================================================//
		
		Thread sendMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() {
                while (true) {
										
					String msg1 = scn.nextLine();
					String msg = r.encrypt_now(msg1,sec_key);
							
					try {
						// write on the output stream
						dos.writeUTF(msg+"#client 0");
						//dos.writeUTF(r.encrypt_now("Client 1 > ",sec_key)+msg+"#client 0");
					} catch (IOException e) {
						e.printStackTrace();
					}                    
                }
            }
        });
		
		//===THREAD TO RECIEVE MESSAGE====================================================================================================//
		
		Thread readMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() {		
                while (true) {
					try{
						String msg = dis.readUTF();
						String m = "";
						m = r.decrypt_now(msg,sec_key);
						
						System.out.println("\nClient 0 : "+m+"\n");
						//System.out.println(m);
						
					}catch(Exception e){}                   
                }
            }
        });
		
		//==================================================================================================================//
		
		
			sendKeyThread.run();
			try{sendKeyThread.wait(500);}catch(Exception e){}	
			recieveKeyThread.run();
			
			
			sendSecretKeyThread.run();
			recieveSecretKeyThread.run();

			show_keys();
			System.out.println("===READY FOR MESSAGE TRANSFER=========================================================");
			sendMessage.start();
			readMessage.start();
			
		
	}
}