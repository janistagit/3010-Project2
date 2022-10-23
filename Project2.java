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
    private static double[][] a; //matrix
    private static double[] b; //result of equations
    private static double [] x; //starting solutions
    private static double error = 0;

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
            a = new double[equations][equations];
            for(int i = 0; i < equations; i++)
            {
                for(int j = 0; j < equations+1; j++)
                {
                    if(j < equations)
                    {
                        System.out.println("Enter a coefficient: ");
                        a[i][j] = scan.nextInt();
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
                a = readFile(fileName); 
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

    private static void jacobi(double[][] a, double[] b, double[] x, double error)
    {
        int kmax = 50;
        double delta = Math.pow(10, -10);
        double epsilon = error;
        int i, j, k, n;
        double diag, sum;
        double[] y = new double[equations];
        n = a.length;
        double normSum;

        for(k = 0; k < kmax; k++)
        {
            normSum = 0;
            y[k] = x[k];
            
            for(i = 0; i < n; i++)
            {
                sum = b[i];
                diag = a[i][i];
                if(Math.abs(diag) < delta)
                {
                    System.out.println("Diagonal element too small.");
                    return;
                }

                for(j = 0; j < n; j++)
                {
                    if(j != i)
                    {
                        sum = sum - (a[i][j] * y[j]);
                    }
                }

                x[i] = sum/diag;
            }
            System.out.println(k + " " + x[k]);

            for(int m = 0; m < x.length; m++)
            {
                normSum = normSum + Math.pow(x[m] - y[m], 2);
            }
            if(Math.sqrt(normSum) < epsilon)
            {
                System.out.println(k + " " + x[k]);
                return;
            }
        }

        System.out.println("Maximum iterations reached.");
        return;
    }

    private static void gaussSeidel(double[][] a, double[] b, double[] x, double error)
    {
        int kmax = 50;
        double delta = Math.pow(10, -10);
        double epsilon = error;
        int i, j, k, n;
        double diag, sum;
        double[] y = new double[equations];
        n = a.length;
        double normSum;

        for(k = 0; k < kmax; k++)
        {
            normSum = 0;
            y[k] = x[k];
            
            for(i = 0; i < n; i++)
            {
                sum = b[i];
                diag = a[i][i];
                if(Math.abs(diag) < delta)
                {
                    System.out.println("Diagonal element too small.");
                    return;
                }

                for(j = 0; j < i-1; j++)
                {
                    sum = sum - (a[i][j] * x[j]);
                }
                for(j = i+1; j < n; j++)
                {
                    sum = sum - (a[i][j] * x[j]);
                }

                x[i] = sum/diag;
            }
            System.out.println(k + " " + x[k]);

            for(int m = 0; m < x.length; m++)
            {
                normSum = normSum + Math.pow(x[m] - y[m], 2);
            }
            if(Math.sqrt(normSum) < epsilon)
            {
                System.out.println(k + " " + x[k]);
                return;
            }
        }

        System.out.println("Maximum iterations reached.");
        return;
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
                    System.out.print(a[i][j] + " ");
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