import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class game {

  enum outcomeConstants {STRIKE, MULTISTRIKE, RED_STRIKE, STRIKER_STRIKE, DEFUNCT_COIN, EMPTY;}

  int option;
  int player = 1;
  int score = 0;
  Result result;
  static int flag = 0;
  static int check =0;

  //Intialize the Board
  CleanStrikeBoard board = new CleanStrikeBoard();
  ArrayList<String> p1 = new ArrayList<String>();
  ArrayList<String> p2 = new ArrayList<String>();
  Players gameplayers = new Players(0, 0, p1, p2);

  static int checkintvalue(int gametype) {

    // The following checks if the value is integer and lies between 1 to 6
    //     :return: value : integer
    while (true) {

      if (gametype == 1) {
        System.out.println("choose between 1-6");
        System.out.println(
            "1. Strike\n" + "2. Multistrike\n" + "3. Red strike\n"
                + "4. Striker strike\n" + "5. Defunct coin\n" + "6. None");

        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input < 1 || input > 6) {
          System.out.println("Please choose a proper Integer between 1 to 6");
          continue;
        } else
          return input;

      } else {
        try {
          Scanner sc = new Scanner(new File("/home/root1/testgame.txt"));
          System.out.println("..........."+check);

          for(int i=1;i<=check;i++) {
            if(sc.hasNextLine())
              sc.nextLine();
          }

            System.out.println("choose between 1-6");
            System.out.println(
                "1. Strike\n" + "2. Multistrike\n" + "3. Red strike\n"
                    + "4. Striker strike\n" + "5. Defunct coin\n" + "6. None");

            int input = Integer.parseInt(sc.nextLine());
            check++;
            if (input < 1 || input > 6) {
              System.out
                  .println("Please choose a proper Integer between 1 to 6");
              continue;
            } else
              return input;
          }
         catch (FileNotFoundException e) {
          e.printStackTrace();
        }

      }
    }
  }

  void letsplay(int gametype) {
    // The following the loop keeps running until a winner is decided.

    while (true) {
      player = (player + 1) % 2;
      if (gameplayers.checkplayerscore() && gameplayers.scoredifference() >= 3
          || board.coinCheck()) {
        result = gameplayers.gameresult();
        if (result.plr1score > result.plr2score) {
          System.out.println("*******WINNER*****" + "   : PLAYER1");
        } else
          System.out.println("*******WINNER*****" + "   : PLAYER2");
        System.out.println(result.plr1score + " " + result.plr2score);
        flag = 1;
        break;
      }
      if (gametype == 1) {
        option = this.checkintvalue(gametype);
        playgame(player, option, gametype);

      } else {
        option = this.checkintvalue(gametype);
        playgame(player, option, gametype);
      }

    }
  }

  void playgame(int player, int option, int gametype) {

    if (option == 1) {
      score = board.strike();
      gameplayers.updateplayerscore(player + 1, score);
      gameplayers.updateplayerhistory(player + 1,
          String.valueOf(outcomeConstants.STRIKE));
      System.out.println("Player1: " + gameplayers.player1score + " Player2: "
          + gameplayers.player2score);
    } else if (option == 2) {
      score = board.multiStrike();
      gameplayers.updateplayerscore(player + 1, score);
      gameplayers.updateplayerhistory(player + 1,
          String.valueOf(outcomeConstants.MULTISTRIKE));
      System.out.println("Player1: " + gameplayers.player1score + " Player2: "
          + gameplayers.player2score);
    } else if (option == 3) {
      score = board.redStrike();
      gameplayers.updateplayerscore(player + 1, score);
      gameplayers.updateplayerhistory(player + 1,
          String.valueOf(outcomeConstants.RED_STRIKE));
      System.out.println("Player1: " + gameplayers.player1score + " Player2: "
          + gameplayers.player2score);
    } else if (option == 4) {
      score = board.striker();
      gameplayers.updateplayerscore(player + 1, score);
      gameplayers.updateplayerhistory(player + 1,
          String.valueOf(outcomeConstants.STRIKER_STRIKE));
      gameplayers.playerfoulcount(player + 1);
      System.out.println("Player1: " + gameplayers.player1score + " Player2: "
          + gameplayers.player2score);
    } else if (option == 5) {
      score = board.defunt();
      gameplayers.updateplayerscore(player + 1, score);
      gameplayers.updateplayerhistory(player + 1,
          String.valueOf(outcomeConstants.DEFUNCT_COIN));
      gameplayers.playerfoulcount(player + 1);
      System.out.println("Player1: " + gameplayers.player1score + " Player2: "
          + gameplayers.player2score);
    } else if (option == 6) {
      score = board.emptyStrike();
      gameplayers.updateplayerscore(player + 1, score);
      gameplayers.updateplayerhistory(player + 1,
          String.valueOf(outcomeConstants.EMPTY));
      gameplayers.playeremptymoves(player + 1);
      System.out.println("Player1: " + gameplayers.player1score + " Player2: "
          + gameplayers.player2score);
    }
  }

  int checkoption() {
    //The following checks if the value is integer and lies between 1 to 2
    //  :return: value : integer
    int value;
    while (true) {
      try {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose a proper Integer between 1 to 2");
        value = sc.nextInt();
        if (value < 1 || value > 2) {
          System.out.println("Please choose a proper Integer between 1 to 2");
          continue;

        } else {
          return value;
        }
      } finally {

      }
    }
  }

  public static void main(String[] args) {
    System.out.println("Choose the type of game you want to play?");
    //Manually giving the input value
    System.out.println("1. Player1 vs Player2");
    // Will take the input value from file
    System.out.println("2. StrikeFilePlayer vs StrikeFilePlayer");
    game gm = new game();

    if (gm.checkoption() == 1) {
      gm.letsplay(1);
    } else {
      gm.letsplay(2);
    }

  }

}

class CleanStrikeBoard {

  //    The Clean Strike Board consists of different possibilites
  //    a player can hit during the course of the game

  int black, red;

  public CleanStrikeBoard() {
    this.black = 9;
    this.red = 1;
  }

  int strike() {
    //    When a player pockets a coin he/she wins a point
    //    The black coin gets reduced by 1
    // return:1 if coin present else return 0
    if (this.black == 0)
      return 0;

    this.black = this.black - 1;
    return 1;
  }

  int multiStrike() {
    //    When a player pockets more than one coin he/she wins 2 points. All, but 2
    //    coins, that were pocketed, get back on to the carrom-board
    //    The black coin and red coin is reset to 7 and 1 respectively.
    //    :return: 2 if Coin present else return 0

    if (this.black == 0)
      return 0;

    this.black = 7;
    this.red = 1;
    return 2;

  }

  int redStrike() {
    //    When a player pockets red coin he/she wins 3 points
    //    The Red Coin gets reduced by 1
    //    :return: 3 if Coin is present else return 0

    if (this.red == 0)
      return 0;
    this.red = this.red - 1;
    return 3;
  }

  int striker() {
    //  When a player pockets the striker he/she loses a point
    return -1;
  }

  int defunt() {
    //    When a coin is thrown out of the carrom-board, due to a strike, the player
    //    loses 2 points, and the coin goes out of play
    //    The Black Coin gets reduced by 1

    if (this.black == 0)
      return 0;
    this.black = this.black - 1;
    return -2;
  }

  int emptyStrike() {
    //No coin is put inside the pocket.
    //    :return: 0
    return 0;
  }

  boolean coinCheck() {
    //    The following checks if the coins are pocketed or not.
    //    :return: True if all coins are pocketed else False

    if (this.black == 0 && this.red == 0)
      return true;
    else
      return false;
  }
}

class Players {
  // The Players class will consists of players score and their history of moves made during the game.
  int player1score = 0;
  int player2score = 0;
  ArrayList<String> player1history = new ArrayList<String>();
  ArrayList<String> player2history = new ArrayList<String>();

  Players(int x, int y, ArrayList<String> sc1, ArrayList<String> sc2) {
    player1score = x;
    player2score = y;
    player1history = sc1;
    player2history = sc2;
  }

  boolean checkplayerscore() {
    if (player1score >= 5 || player2score >= 5)
      return true;
    return false;
  }

  void updateplayerscore(int player, int score) {
    //The following updates a player score based on the player
    //param player: integer
    //param score: integer
    if (player == 1) {
      this.player1score = this.player1score + score;

      if (this.player1score < 0)
        this.player1score = 0;
    } else {
      this.player2score = this.player2score + score;
      if (this.player2score < 0)
        this.player2score = 0;
    }

  }

  void updateplayerhistory(int player, String outcome) {
    // The following updates a player history based on the player
    // :param player: integer
    // :param outcome: String
    if (player == 1) {
      this.player1history.add(outcome);
    } else {
      this.player2history.add(outcome);
    }
  }

  Result gameresult() {
    //  The following checks the score of a player and returns the result.
    //  :return: object containing result, player1score, player2score
    if (this.player1score > this.player2score
        && this.player1score - this.player2score >= 3)
      return new Result("Player1", this.player1score, this.player2score);

    else if ((this.player1score < this.player2score) && (
        this.player2score - this.player1score >= 3)) {
      return new Result("Player2", this.player1score, this.player2score);
    }

    return new Result("Draw", this.player1score, this.player2score);

  }

  void playeremptymoves(int player) {
    // When a player does not pocket a coin for 3 successive turns he/she loses a point
    //The following function checks last 3 moves of a player to see if the outcome is equal to Empty
    //  :param player: integer

    ArrayList<String> moves = new ArrayList<String>();
    if (player == 1) {
      moves = this.player1history;
    } else {
      moves = this.player2history;
    }

    int count = 0;
    int j = 0;
    for (int i = moves.size() - 1; i >= 0; i--) {
      if (j > 3) {
        break;
      }
      if (moves.get(i) == String.valueOf(game.outcomeConstants.EMPTY)) {

        count = count + 1;
      }
      j = j + 1;
    }
    if (count >= 3)
      this.updateplayerscore(player, -1);
  }

  void playerfoulcount(int player) {
    //When a player ​fouls 3
    //times(a ​foul is a turn where a player loses, at least, 1point), he / she
    //loses an additional point The following function checks last 3 moves of
    // a player to see if the outcome is equal to DEFUNCT_COIN or STRIKER_STRIKE

    //  param player:integer "" " "
    ArrayList<String> moves = new ArrayList<String>();
    if (player == 1) {
      moves = this.player1history;
    } else {
      moves = this.player2history;
    }

    int count = 0;
    int j = 0;
    for (int i = moves.size() - 1; i >= 0; i--) {
      if (j > 3) {
        break;
      }
      if ((moves.get(i))
          .equals(String.valueOf(game.outcomeConstants.DEFUNCT_COIN)) || (moves
          .get(i)
          .equals(String.valueOf(game.outcomeConstants.STRIKER_STRIKE)))) {

        count = count + 1;
      }
      j = j + 1;
    }
    if (count >= 3)
      this.updateplayerscore(player, -1);

  }

  int scoredifference() {
    // "" " The following presents the score difference of a players based
    // on higher points
    // return:Difference of score between two players "" "
    if (this.player1score > this.player2score)
      return this.player1score - this.player2score;
    else
      return this.player2score - this.player1score;
  }

}

class Result {
  String res;
  int plr1score;
  int plr2score;

  Result(String res, int plr1score, int plr2score) {
    this.res = res;
    this.plr1score = plr1score;
    this.plr2score = plr2score;
  }
}


