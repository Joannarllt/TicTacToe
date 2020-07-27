
public class Hello{

  public static void main() {
      /// some variables probably go here, dunno what
      
    int eX = 0; 
    int Oh = 0;
    int both = eX|Oh;
    boolean Xwon = false; 
    boolean Owon = false;
      
      
    for (int max=0; max<9; max++) { // default termination if game does not end normal
        /// 1. Display the board..
      int aBit = 1;
      int doing = 0;
      for (int ro=1; ro<=3; ro++) { // do three rows..
          /// prepare to display one row..
          System.out.println("");
          if (ro>1) System.out.println("---+---+---");
      
          for (int co=1; co<=3; co++) { // do 3 squares in that row..
              /// display one square
              if (co>1) System.out.print(" | ");
              else System.out.print(" ");
        
              aBit = aBit<<1;
              doing++;
              if ((eX&aBit) != 0) System.out.print("X");
              else if ((Oh&aBit) != 0) System.out.print("O");
              else System.out.print(doing);
      } // end of 3 squares
    } // end of 3 rows
    System.out.println("");
    System.out.println("");
    
      /// 2. Is game over?
      if (Xwon) break;
      if (Owon) break;
      if (eX+Oh == 0x3FE) break;
      System.out.println("");
      
      /// 3. Accept X play
      System.out.print("Your play: ");
      while (true) {
        doing = Zystem.ReadInt();
        if (doing>9) doing = 0; // so a single test catches all off-board plays
        if (doing<1) break; // invalid input, user wants out
        aBit = 1<<doing;
        if (((eX|Oh)&aBit) == 0) { // valid play..
          eX = eX|aBit; // mark X as having played this square
          break;}
        System.out.print("Taken! "); // ..then go back for another input
      } // end of input loop
      if (doing<1) break; // invalid input
      
      
      if ((eX&0xE)==0xE) Xwon = true; // top row
      else if ((eX&0x70)==0x70) Xwon = true; // middle row
      else if ((eX&0x380)==0x380) Xwon = true; // bottom row
      else if ((eX&0x92)==0x92) Xwon = true; // left column
      else if ((eX&0x124)==0x124) Xwon = true; // middle column
      else if ((eX&0x248)==0x248) Xwon = true; // right column
      else if ((eX&0x222)==0x222) Xwon = true; // lefttop > bottom right diagonal
      else if ((eX&0xA8)==0xA8) Xwon = true; // righttop > bottomleft diagonal
      
      aBit = 1;
      doing = 0;
      int best = -1;
      int chose = 0;
      both = eX|Oh;
      int score = 0;
      int test = 0;
      int mask = 0;
      
      /// 4. Calculate O play
      aBit = 1;
      
      for (doing=1; doing<=9; doing++) { // try all available squares
        aBit = aBit<<1;
        if ((both&aBit) !=0) continue; // already played, go to next square
        score = 0;
        test = Oh|aBit; // all the O plays + doing
        /// if doing wins, score = 99
        if ((test&0xE)==0xE) score = 99; // top row wins
        else if ((test&0x70)==0x70) score = 99; // middle row
        else if ((test&0x380)==0x380) score = 99; // bottom row
        else if ((test&0x92)==0x92) score = 99; // left column
        else if ((test&0x124)==0x124) score = 99; // middle column
        else if ((test&0x248)==0x248) score = 99; // right column
        else if ((test&0x222)==0x222) score = 99; // topleft bottom right diagonal
        else if ((test&0xA8)==0xA8) score = 99; // topright bottom left diagonal
        /// if doing blocks X win, score = score+22
        test = eX|aBit; // all the X plays + doing
        if ((test&0xE) == 0xE) score += 22;
        else if ((test&0x70)==0x70) score += 22; // middle row
        else if ((test&0x380)==0x380) score += 22; // bottom row
        else if ((test&0x92)==0x92) score += 22; // left column
        else if ((test&0x124)==0x124) score += 22; // middle column
        else if ((test&0x248)==0x248) score += 22; // right column
        else if ((test&0x222)==0x222) score += 22; // topleft bottom right diagonal
        else if ((test&0xA8)==0xA8) score += 22; // topright bottom left diagonal
        /// for each row/column/diagonal doing is in, if doing does not fill it,
        mask = 0xE; // top row
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in top row
          if ((both&mask)==0) score++; // top row is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;} // X is empty, so must have single O
        mask = 0x70; // 2nd row, just like the top row..
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in middle row
          if ((both&mask)==0) score++; // middle row is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;}
        mask = 0x380;
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in bottom row
          if ((both&mask)==0) score++; // bottom row is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;}
        mask = 0x92;
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in left column
          if ((both&mask)==0) score++; // left column is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;}
        mask = 0x124;
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in middle column
          if ((both&mask)==0) score++; // middle column is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;}
        mask = 0x248;
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in right column
          if ((both&mask)==0) score++; // right column is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;}
        mask = 0x222; // down diagonal
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in d.diag
            if ((both&mask)==0) score++; // d.diag is empty
            else if ((Oh&mask)==0) score = score+2;
            else if ((eX&mask)==0) score = score+4;}
        mask = 0xA8;
        if ((mask&aBit) !=0) if (((both|aBit)&mask) != mask) { // doing is in up.diag
          if ((both&mask)==0) score++; // up.diag is empty
          else if ((Oh&mask)==0) score = score+2; // O is empty, so must have single X
          else if ((eX&mask)==0) score = score+4;}
        if (score>best) { // best square so far..
          chose = doing;
          best = score;} // otherwise ignore this square, we already have better
    } // end of heuristic loop
    if (chose==0) { // huh? this shouldn't happen..
        //System.out.println("Something went wrong");
        break;}
    aBit = 1<<chose;
    Oh = Oh|aBit;
      
    if ((Oh&0xE)==0xE) Owon = true; // top row
    else if ((Oh&0x70)==0x70) Owon = true; // middle row
    else if ((Oh&0x380)==0x380) Owon = true; // bottom row
    else if ((Oh&0x92)==0x92) Owon = true; // left column
    else if ((Oh&0x124)==0x124) Owon = true; // middle column
    else if ((Oh&0x248)==0x248) Owon = true; // right column
    else if ((Oh&0x222)==0x222) Owon = true; // lefttop > bottom right diagonal
    else if ((Oh&0xA8)==0xA8) Owon = true; // righttop > bottomleft diagonal
    } // end of outer game loop
    /// congratulate winner
    if (Xwon) System.out.println("You won, oh well.");
    else if (Owon) System.out.println("I won, I won, I won!");
    else if (both == 0x3FE) System.out.println("Cat's game.");
  } // ~main 
} // ~Hello 