# PPTHangman

This is a simple text-based Hangman game created during a Personal Programming Tutorial to teach object-oriented programming

# Specification

1. A session of hangman consists of any number of hangman games (until we quit the application)
2. A hangman game can be played with any number of players, which we ask for at the start of the game
3. At the start of the game, we pick an arbitrary "leader", before asking to start a new game
4. In every hangman session:
   1. We ask the leader to pick a word/sentence
   2. We ask every player in turn to either guess a letter, or guess the whole word
   3. If they get the whole word correctly, they get 10 points, and the leader loses 5, and the next game starts
   4. If they get a single letter correct, they get 1 point and the according letter positions are revealed
   5. If they don't make a correct guess, we take 1 life away from the game (lives are shared amongst all non-leaders, in that given game)
   6. We continue until team runs out of lives, in which case the leader gets 10 points, or someone guesses the word
   7. We ask if we want to play another game (session)
      1. If yes, cycle the leader and start again
5. We print the leaderboards
6. We exit the application

# Main Collaborators

- Akhtar Syed
- Victor-Gabriel Apostol
- Yoram Boccia
- Ossama Chaib
- Quok Chong
- Dhru Devalia Pablo Gamito
- Hadrian Lim Wei Heng
