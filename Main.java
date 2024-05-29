import java.util.Scanner;

public class Main {
    // x =1
    public static boolean xWin(int[][] board, int size) {

        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += (board[i][j] == 1 ? 1 : 0);
                colSum += (board[j][i] == 1 ? 1 : 0);
            }
            if (rowSum == size || colSum == size) {
                return true;
            }
        }

        int diag1 = 0;
        int diag2 = 0;
        for (int i = 0; i < size; i++) {
            diag1 += (board[i][i] == 1 ? 1 : 0);
            diag2 += (board[i][size - i - 1] == 1 ? 1 : 0);
        }
        if (diag1 == size || diag2 == size) {
            return true;
        }

        return false;
    }

    // o = 2
    public static boolean oWin(int[][] board, int size) {
        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += (board[i][j] == 2 ? 1 : 0);
                colSum += (board[j][i] == 2 ? 1 : 0);
            }
            if (rowSum == size || colSum == size) {
                return true;
            }
        }

        int diag1 = 0;
        int diag2 = 0;
        for (int i = 0; i < size; i++) {
            diag1 += (board[i][i] == 2 ? 1 : 0);
            diag2 += (board[i][size - i - 1] == 2 ? 1 : 0);
        }
        if (diag1 == size || diag2 == size) {
            return true;
        }

        return false;
    }

    // doesnt check if board is full.
    public static boolean cat(int[][] board, int size) {

        if (!xWin(board, size) && !oWin(board, size)) {
            return true;
        }
        return false;
    }

    public static int countEmptyCells(int[][] board, int size) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0)
                    count++;
            }
        }
        return count;
    }

    public static void printBoard(int[][] board, int size) {
        System.out.println("Board:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char temp;
                if (board[i][j] == 1) {
                    temp = 'X';
                } else if (board[i][j] == 2)
                    temp = 'O';
                else
                    temp = ' ';
                System.out.print("[" + temp + "]");
            }
            System.out.println();
        }

    }

    public static void printTable(int size) {
        System.out.println("\nPick the character where you want to play: \n");
        char starting = '!';
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("[" + starting + "]");
                starting++;
            }
            System.out.println();
        }
    }

    public static int[][] xTurn(int[][] board, int size, Scanner scanner) {
        while (true) {
            String input = scanner.next();
            char choice = input.charAt(0);

            if (choice >= '!' && choice <= '~') {
                int position = choice - '!';
                int row = position / size;
                int col = position % size;

                if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == 0) {
                    board[row][col] = 1;
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid position.");
                }
            } else {
                System.out.println("Invalid input. Please enter a printable character.");
            }
        }

        return board;
    }

    public static boolean isMovesLeft(int[][] board, int size) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == 0)
                    return true;
        return false;
    }

    public static int evaluate(int[][] board, int size) {
        for (int row = 0; row < size; row++) {
            if (board[row][0] != 0) {
                boolean win = true;
                for (int col = 1; col < size; col++) {
                    if (board[row][col] != board[row][0]) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    if (board[row][0] == 2)
                        return +10;
                    else if (board[row][0] == 1)
                        return -10;
                }
            }
        }

        for (int col = 0; col < size; col++) {
            if (board[0][col] != 0) {
                boolean win = true;
                for (int row = 1; row < size; row++) {
                    if (board[row][col] != board[0][col]) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    if (board[0][col] == 2)
                        return +10;
                    else if (board[0][col] == 1)
                        return -10;
                }
            }
        }

        if (board[0][0] != 0) {
            boolean win = true;
            for (int i = 1; i < size; i++) {
                if (board[i][i] != board[0][0]) {
                    win = false;
                    break;
                }
            }
            if (win) {
                if (board[0][0] == 2)
                    return +10;
                else if (board[0][0] == 1)
                    return -10;
            }
        }

        if (board[0][size - 1] != 0) {
            boolean win = true;
            for (int i = 1; i < size; i++) {
                if (board[i][size - i - 1] != board[0][size - 1]) {
                    win = false;
                    break;
                }
            }
            if (win) {
                if (board[0][size - 1] == 2)
                    return +10;
                else if (board[0][size - 1] == 1)
                    return -10;
            }
        }

        return 0;
    }

    public static int minimax(int[][] board, int depth, boolean isMax, int size) {
        int score = evaluate(board, size);

        if (score == 10)
            return score;
        if (score == -10)
            return score;
        if (!isMovesLeft(board, size))
            return 0;

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;
                        best = Math.max(best, minimax(board, depth + 1, !isMax, size));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        best = Math.min(best, minimax(board, depth + 1, !isMax, size));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    public static int[][] oTurn(int[][] board, int size) {
        int bestVal = -1000;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 2;
                    int moveVal = minimax(board, 0, false, size);
                    board[i][j] = 0;

                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        board[bestRow][bestCol] = 2;
        return board;
    }

    public static String gameLoop(Scanner scanner, int size) {
        String winner = " ";

        int[][] board = new int[size][size];
        // printBoard(board, size);

        while (!xWin(board, size) && !oWin(board, size) && cat(board, size)) {
            printBoard(board, size);
            printTable(size);

            board = xTurn(board, size, scanner);
            if (!isMovesLeft(board, size))
                break;
            board = oTurn(board, size);
        }
        if (xWin(board, size))
            winner = "X";
        else if (oWin(board, size))
            winner = "O";
        else
            winner = "TIE";
        printBoard(board, size);
        return winner;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter size of board to play on: (3,9)");
        int size = scanner.nextInt();
        if (size > 9 || size < 3) {
            System.out.println(size + " is out of range!!");
            scanner.close();
            return;
        }

        System.out.println("Winner is " + gameLoop(scanner, size) + "!");

        scanner.close();
    }
}
