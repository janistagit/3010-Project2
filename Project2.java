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
    private static double [] x2;
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
        }

        System.out.println();
        System.out.println("Entered matrix: ");
        printMatrix();

        Scanner keyb = new Scanner(System.in);
        System.out.println("\nEnter the stopping error: ");
        error = keyb.nextDouble();

        System.out.println("\nEnter the starting solution values one by one.");
        x = new double[equations];
        x2 = new double[equations];
        for(int i = 0; i < equations; i++)
        {
            System.out.println("Enter the value: ");
            x[i] = keyb.nextDouble();
            x2[i] = x[i];
        }

        System.out.println("\nJacobi's Method:");
        jacobi(a, b, x, error);
        System.out.println("\n\nGauss-Seidel Method:");
        gaussSeidel(a, b, x2, error);
        System.out.println("\n");
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
            for(int m = 0; m < x.length; m++)
            {
                y[m] = x[m];
            }
            
            for(i = 0; i < n; i++)
            {
                sum = b[i];
                diag = a[i][i];
                if(Math.abs(diag) < delta)
                {
                    System.out.println("\nDiagonal element too small.");
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

            for(int m = 0; m < x.length; m++)
            {
                normSum = normSum + Math.pow(x[m] - y[m], 2);
            }

            System.out.print("Iteration " + (k+1) + ": [");
            for(int m = 0; m < x.length-1; m++)
            {
                System.out.printf("%.4f ", x[m]);
            }
            System.out.printf("%.4f", x[x.length-1]);
            System.out.print("]T");
            System.out.print(" Error: " + Math.sqrt(normSum) + "\n");

            
            if(Math.sqrt(normSum) < epsilon)
            {
                System.out.print("\nSolution:" + " [");
                for(int m = 0; m < x.length-1; m++)
                {
                    System.out.printf("%.4f ", x[m]);
                }
                System.out.printf("%.4f", x[x.length-1]);
                System.out.print("]T");                
                return;
            }
        }

        System.out.println("\nMaximum iterations reached.");
        System.out.print("\nSolution:" + " [");
                for(int m = 0; m < x.length-1; m++)
                {
                    System.out.printf("%.4f ", x[m]);
                }
                System.out.printf("%.4f", x[x.length-1]);
                System.out.print("]T");
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
            for(int m = 0; m < x.length; m++)
            {
                y[m] = x[m];
            }

            for(i = 0; i < n; i++)
            {
                sum = b[i];
                diag = a[i][i];
                if(Math.abs(diag) < delta)
                {
                    System.out.println("\nDiagonal element too small.");
                    return;
                }

                for(j = 0; j <= i-1; j++)
                {
                    sum = sum - (a[i][j] * x[j]);
                }
                for(j = i+1; j < n; j++)
                {
                    sum = sum - (a[i][j] * x[j]);
                }

                x[i] = sum/diag;
            }

            for(int m = 0; m < x.length; m++)
            {
                normSum = normSum + Math.pow(x[m] - y[m], 2);
            }

            System.out.print("Iteration " + (k+1) + ": [");
            for(int m = 0; m < x.length-1; m++)
            {
                System.out.printf("%.4f ", x[m]);
            }
            System.out.printf("%.4f", x[x.length-1]);
            System.out.print("]T");
            System.out.print(" Error: " + Math.sqrt(normSum) + "\n");

            if(Math.sqrt(normSum) < epsilon)
            {
                System.out.print("\nSolution:" + " [");
                for(int m = 0; m < x.length-1; m++)
                {
                    System.out.printf("%.4f ", x[m]);
                }
                System.out.printf("%.4f", x[x.length-1]);
                System.out.print("]T");
                return;
            }
        }

        System.out.println("\nMaximum iterations reached.");
        System.out.print("\nSolution:" + " [");
                for(int m = 0; m < x.length-1; m++)
                {
                    System.out.printf("%.4f ", x[m]);
                }
                System.out.printf("%.4f", x[x.length-1]);
                System.out.print("]T");
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