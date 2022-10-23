/*
 * Project 2
 * By Janista Gitbumrungsin
 * CS3010.02 Fall 2022
 * Dr. Lajpat Rai Raheja
 */
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class Project2
{
    private static int equations = 11;
    private static int option = 0;
    private static double[][] matrix;
    private static double[] b;

    public static void main(String[] args)
    {
        System.out.println("\nJACOBI'S METHOD AND GAUSS-SEIDEL METHOD");
        Scanner scan = new Scanner(System.in);
        while(equations > 10 || equations < 2)
        {
            System.out.println("Please enter the number of equations in the system (less than or equal to 10): ");
            equations = scan.nextInt();
        }
        while(option != 1 && option != 2)
        {
            System.out.println("\nWould you like to:\n1. Enter the coefficients from the command line\n2. Read a file");
            option = scan.nextInt();
        }

        b = new double[equations];

        if(option == 1)
        {
            System.out.print("\n");
            matrix = new double[equations][equations];
            for(int i = 0; i < equations; i++)
            {
                for(int j = 0; j < equations+1; j++)
                {
                    if(j < equations)
                    {
                        System.out.println("Enter matrix coefficient: ");
                        matrix[i][j] = scan.nextInt();
                    }
                    else
                    {
                        System.out.println("Enter b value: ");
                        b[i] = scan.nextInt();
                    }
                    
                }
            }
        }
        else if(option == 2)
        {
            Scanner kb = new Scanner(System.in);
            System.out.println("\nEnter the name of the file: ");
            String fileName = kb.nextLine();
            
            try
            {
                matrix = readFile(fileName); 
            }
            catch(IOException exception)
            {
                System.out.println("Error with file reading.");
                System.exit(0);
            }
            kb.close();
        }
        scan.close();

        System.out.println();
        System.out.println("Entered matrix: ");
        printMatrix();
    }

    private static double[][] readFile(String fileName) throws IOException
    {
        File myFile = new File(fileName);
        Scanner inputFile = new Scanner(myFile);
        double[][] output = new double[equations][equations];

        for(int i = 0; i < equations; i++)
        {
            for(int j = 0; j < equations+1; j++)
            {
                if(j < equations)
                {
                    output[i][j] = inputFile.nextInt();
                }
                else
                {
                    b[i] = inputFile.nextInt();
                }
            }
        }

        inputFile.close();
        return output;
    }

    public static void printMatrix()
    {
        for(int i = 0; i < equations; i++)
        {
            for(int j = 0; j < equations+1; j++)
            {
                if(j < equations)
                {
                    System.out.print(matrix[i][j] + " ");
                }
                else
                {
                    System.out.print(b[i] + " ");
                }
            }
            System.out.print("\n");
        }

    }
}