package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c == 0 || c == r) 1
      else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def loop(cs: List[Char], cnt: Int): Int ={
        if (! cs.isEmpty){
          if (cs.head == ')') if (cnt<0) loop(cs.tail, cnt + 1) else 1
          else if (cs.head == '(') loop(cs.tail, cnt - 1) else loop(cs.tail, cnt)
        }
        else cnt
      }

      loop(chars, 0) ==0
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      def count(m: Int, coin: List[Int], cnt: Int): Int ={
        if (m == 0 || coin.isEmpty || m < coin.head) 0
        else{
          if (m == coin.head) 1
          else count(m - coin.head, coin, cnt) + count(m, coin.tail, cnt)
        }
      }
      count(money, coins.sorted, 0)
    }
  }
