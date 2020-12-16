package myairlinesserver2;

import java.net.ServerSocket;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Server2 {
    static int PortNo;
	//public ServerSocket datasoc;
	 public static void main(String args[]) throws Exception
	 
	    {
		 	
		 	//System.out.println(args[0]);
		 	if (args.length == 1){
		 		//System.out.println(args[0]);
		 		PortNo=Integer.parseInt(args[0]);
		 		
		 	}
		 	else {
		 		PortNo=1503;
		 	}
	        ServerSocket soc=new ServerSocket(PortNo);
	        ServerSocket datasoc=new ServerSocket(PortNo-1);
	        System.out.println("MyAirlines Server 2 Started on Port Number "+ PortNo);
	        while(true)
	        {
	            System.out.println("Waiting for Connection ...");
	            //System.out.println(123);
	            transferfile2 t=new transferfile2(soc.accept(),datasoc);
	        }
	    }
}
