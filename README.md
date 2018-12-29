This WEA is Created from Start to Finish. 

Apply Basic WEA Patterns. In this project, the full set of mature Architectural patterns are set, and any
related patterns they imply.

• Front Controller

• Dispatcher

• Command

• Template View (or Transform View)

• Domain Object

• Input Mapper

• Output Mapper

• Finder

• Table Data Gateway

• Proxy (Lazy Load)

• Identity Map

• Domain Object Factory

• Unit of Work

• Optimistic Concurrency Management


2 Description

The beginnings of a Pokemon game. Players will be able to:
1. register
2. login
3. logout
4. upload decks
5. view decks
6. view deck
7. list players
8. challenge players
9. list challenges
10. withdraw challenges
11. refuse challenges
12. accept challenges
13. list games
14. view board
15. view hand
16. view discard pile
17. bench pokemon
18. evolve pokemon
19. play energy
20. play trainer
21. end turn
22. retire from a game

2.1 Constraints

The game has a few rules to keep in mind:

1. you must have more than one player to play a game
2. you cannot issue or accept a challenge without a deck
3. you can only retire from games you are playing
4. you can only view your hand in a game you are playing
5. you cannot have users with the same identification
6. you cannot challenge yourself
7. both players in a game may look at either discard pile
8. a deck must have 40 cards
9. there is no shuffling! Decks start in the order they are uploaded, and when you play with them, you draw cards (first
draw is the first line of the uploaded deck, etc).
10. It is the challenger’s turn first.
11. The challenger takes the first turn, starting the game with one card drawn
12. The challengee gets their first card when the challenger ends their first turn
13. there are no active pokemon!
14. one card is drawn at the beginning of each turn after the first (you can think of the start if the first player’s turn as
happening when a challenge is accepted)
15. you may only play one energy per turn
16. when a pokemon is evolved, it keeps its energy, the basic pokemon does not go into the discard pile, but is still "visible"
17. when a trainer is played, it is moved to the top of the discard pile (nothing else happens)
18. Each hand can have at most 7 cards
19. If a turn ends and that player has more than 7 cards, the oldest card in their hand is moved to the top of the discard
pile
20. Optimistic Concurrency Management should cause failure whenever submitted versions don’t match for all Use Cases
except Retire
21. A player may retire without the need of a version: Optimistic Concurrency Management should just allow retiring
whenever, as long as the player is in the game.
22. The same deck may be used in multiple games
23. A player may only have a single open challenge against another player, but may challenge as many different players as
they wish
