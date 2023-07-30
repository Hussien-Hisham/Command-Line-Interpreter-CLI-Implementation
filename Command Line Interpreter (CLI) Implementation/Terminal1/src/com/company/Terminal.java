package com.company;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.time .*;
 class Parser {
    String commandName;

    String[] args;
    public boolean parse(String input){
        int temp = 0;
        String temp1;
        for (int i=0;i<input.length();i++){
            if (input.charAt(i)==' '){
                temp=i;
                break;
            }

        }
        commandName=input.substring(0,temp);
        temp1=input.substring(temp+1,input.length());
        args=temp1.split(" ");



        return true;

    }
    public String getCommandName(){
        return commandName;

    }
    public String[] getArgs(){
        return args;


    }

    //This method will divide the input into commandName and args
//where "input" is the string command entered by the user

}

public class Terminal {



        static Parser  parser;
        //Implement each command in a method, for example:
        public static void chooseCommandAction() throws IOException {

            while (true) {
                Parser parser1 = new Parser();
                Scanner s1=new Scanner(System.in);
                String s=s1.nextLine();
                parser1.parse(s);
                if (parser1.getCommandName().equals("cd")) {
                    cd(parser1.getArgs());
                }
                else if (s.equals("pwd")) {
                    pwd();
                }
                else if (s.equals("ls")) {
                    ls();
                }
                else if (s.equals("ls_r")) {
                    ls_r();
                }
                else  if (parser1.getCommandName().equals("echo")) {
                    echo(parser1.getArgs());
                }
                else if (s.equals("cd")) {
                    cd();
                }
                else if (s.equals("date")) {
                    LocalDate myObj = LocalDate.now();
                    System.out.println(myObj);
                }
                else if (parser1.getCommandName().equals("touch")){
                    touch(parser1.getArgs());
                }
                else if (parser1.getCommandName().equals("mkdir")){
                    mkdir(parser1.getArgs());
                }
                else if (parser1.getCommandName().equals("cat")){
                    cat(parser1.getArgs());
                }
                else if (parser1.getCommandName().equals("rm")){
                    rm(parser1.getArgs());
                }
                else if (parser1.getCommandName().equals("rmdir")){
                    rmdir(parser1.getArgs());
                }
                else if (parser1.getCommandName().equals("cp")){
                    cp(parser1.getArgs());
                }
                else if (parser1.getCommandName().equals("cp_r")){
                    cp_r(parser1.getArgs());
                }


            }


        }
        public static String pwd(){

            String current=System.getProperty("user.dir");
            System.out.println("Current Directory = " + current);
            return current;

        }
        public static void echo(String[] args){


            String arr=new String();
            for ( int i = 0; i < args.length; i++ )
            {
                arr+=args[i]+" ";

            }
            System.out.println(arr);


        }
        public static void ls() {

            File file = new File(System.getProperty("user.dir"));

            // returns an array of all files
            String[] fileList = file.list();
            Arrays.sort(fileList);

            for (String str : fileList) {

                System.out.println(str);

            }
        }
        public static void ls_r() {


            File file = new File(System.getProperty("user.dir"));

            // returns an array of all files
            String[] fileList = file.list();

            Arrays.sort(fileList, Collections.reverseOrder((String.CASE_INSENSITIVE_ORDER)));

            System.out.println(Arrays.toString(fileList));

        }
        public static void cd(){

            String home=System.getProperty("user.home");

            System.setProperty("user.dir",System.getProperty("user.home"));

        }
        public static void cd(String[] args){
            if (args.length>1){System.out.println("enter 1 argument only");}
            String arr = new String();
            for (int i=0;i<args.length;i++)
            {
               arr+=args[i];
            }
            if (arr.contains("..")){
                String current=System.getProperty("user.dir");
                Path v = Paths.get(current).getParent();
                System.setProperty("user.dir", String.valueOf(v));}
            else {

                File file=new File(args[0]);

                if( !(file.isAbsolute()) ){
                    file = new File(pwd() + "\\" + args[0]);
                    if(file.exists()){
                    System.setProperty("user.dir", String.valueOf(file));}
                    else {
                        System.out.println("ERROR: no such file or directory!");
                    }
                }
                else {

                System.setProperty("user.dir", args[0]);}}

        }
    public static void touch(String[] args){
        if (args.length>1){System.out.println("enter 1 argument only");}
        else {
            String arr = new String();
            String current = pwd();
            for (int i = 0; i < args.length; i++) {
                arr += args[i];
            }
            File file = new File(arr);

            if (!(file.isAbsolute())) {
                file = new File(current + "\\" + arr);
            }


            boolean next;
            try {
                next = file.createNewFile();
                if (next) {
                    System.out.println("file created " + file.getCanonicalPath()); //returns the path string

                } else {
                    System.out.println("File already exist at location: " + file.getCanonicalPath());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void mkdir(String[] args) {


        String arr = new String();
        String current=pwd();
        boolean flag=true;

        for (int i=0;i<args.length;i++)
        {
            File file = new File(args[i]);
            if( !(file.isAbsolute()) )
            {
                file = new File(current + "\\" + args[i]);

            }
            if(!file.exists())
            {

                file.mkdirs();
                flag=false;}

            else
            {
                System.out.println("Dirictor already exists.");
            }
        }




    }
    public static void cat(String[] args) throws IOException {
        byte[] fileContent;
        if (args.length>2){System.out.println("enter 2 argument only");}
        else {

            for (int i = 0; i < args.length; i++) {
                File file = new File(args[i]);
                if (file.exists() && file.isFile()) {

                    fileContent = Files.readAllBytes(file.toPath());


                    // Iterates through each bytes
                    for (int j = 0; j < fileContent.length; j++) {

                        // Printing each char to the screen
                        {
                            System.out.print((char) (fileContent[j]));

                        }

                    }
                    System.out.println("\n");
                } else {
                    System.out.println("ERROR: " + file.getName() + " file doesn't exists!");
                }
            }
        }

    }
    public static void rm(String[] args) {
        if (args.length==2){System.out.println("enter 1 argument only");}
        else {
        for (int i=0;i<args.length;i++)
        {
            File file = new File(args[i]);
            if( !(file.isAbsolute()) )
                file = new File(pwd() + "\\" + args[i]);

            // Checking if it exists
            if(file.exists()) {

                // Checking if it's a directory and not a file
                if(file.isFile()) {

                    // Deleting the file
                    file.delete();

                } else {
                    System.out.println("ERROR: only files can be deleted!");
                }

            } else {
                System.out.println("ERROR: no such file or directory!");
            }

    }}}
    public void deleteDir(File file) throws IOException
    {

        if (file.isDirectory())
        {

            /*
             * If directory is empty, then delete it
             */
            if (file.list().length == 0)
            {
                deleteEmptyDir(file);
            }
            else
            {
                // list all the directory contents
                File files[] = file.listFiles();

                for (File fileDelete : files)
                {
                    /*
                     * Recursive delete
                     */
                    deleteDir(fileDelete);
                }

                /*
                 * check the directory again, if empty then
                 * delete it.
                 */
                if (file.list().length == 0)
                {
                    deleteEmptyDir(file);
                }
            }

        }

    }

    private void deleteEmptyDir(File file)
    {
        file.delete();
        System.out.println("Directory is deleted : " + file.getAbsolutePath());
    }


    public static void rmdir(String[] args) throws IOException {
        if (args.length>1){System.out.println("enter 1 argument only");}
        else {
        for (int i = 0; i < args.length; i++) {

             File file = new File(pwd());
            if (args[i].contains("*")) {

                if (!file.exists()) {
                    System.out.println("Directory does not exist.");
                } else {

                    Terminal deleteDirectory = new Terminal();
                    deleteDirectory.deleteDir(file);
                }
            }
            else {
                File file1=new File(args[i]);
                    Terminal deleteDirectory = new Terminal();
                    deleteDirectory.deleteDir(file1);
            }
        }}}
    public static void cp(String[] args) {
        FileInputStream instream = null;
        FileOutputStream outstream = null;
        try {
            if (args.length>2){
                System.out.println("please enter 2 arguments only");
            }
            else {
            for (int i = 0; i < args.length - 1; i++) {

                File source = new File(args[i]);
                File dest = new File(args[i + 1]);

                instream = new FileInputStream(source);
                outstream = new FileOutputStream(dest);

                byte[] buffer = new byte[1024];

                int length;
                /*copying the contents from input stream to
                 * output stream using read and write methods
                 */
                while ((length = instream.read(buffer)) > 0) {
                    outstream.write(buffer, 0, length);
                }

                //Closing the input/output file streams
                instream.close();
                outstream.close();

                System.out.println("File copied successfully!!");

            }}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void cp_r(String[] args) {
        System.out.println(args[0] + args[1]);
        if (args.length != 2) {
            System.out.println("Wrong number of arguments");
            return;
        }
        File input = new File(args[0]);
        if (!input.getAbsolutePath().equals(args[0])) {
            String path = pwd() + File.separator + args[0];
            input = new File(path);
        }

        File output = new File(args[1]);
        if (!output.getAbsolutePath().equals(args[1])) {
            String path = pwd()+ File.separator + args[1];
            System.out.println(path);
            output = new File(path);
        }

        if (!input.exists()) {
            System.out.println("Directory not found");
            return;
        }

        if (!output.exists()) {
            try {
                output.mkdirs();


            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }

        }
        if (!input.isDirectory() || !output.isDirectory()) {
            System.out.println("Arguments should be directories not Files");
            return;
        }


        String[] ar = input.list();
        assert ar != null;
        for (String file : ar) {
            File dest = new File(output.getAbsolutePath() + File.separator + file);
            File source = new File(input.getAbsolutePath() + File.separator + file);
            if (source.isDirectory()) {
                cp_r(new String[]{source.getAbsolutePath(), dest.getAbsolutePath()});
            } else {
                try {
                    cp(new String[]{source.getAbsolutePath(), dest.getAbsolutePath()});
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }




        public static void main(String[] args) throws IOException {

           chooseCommandAction();
           System.out.println();
        }
        // ...
//This method will choose the suitable command method to be called
}

