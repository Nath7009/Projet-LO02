# Projet-LO02
Projet LO02 A18 – The Other Hat Trick Y. Langeron - G. Doyen

Projet LO02
The Other Hat Trick
 Il vous est proposé dans ce projet de concevoir avec UML et développer en Java une version
 électronique du jeu de cartes « The Other Hat Trick ».
1. Règles du jeu
 Les règles du jeu vous sont données en annexe. Elles servent de document de référence pour
 l'expression des besoins du logiciel développé.
 Au-delà, on considère aussi les besoins suivants :
  • L’application devra permettre de jouer à plusieurs joueurs qui seront des joueurs physiques
  auxquels s'ajoutent des joueurs virtuels. Dans le cas d’un joueur virtuel, le jeu devra lui
  attribuer une stratégie de jeu simple mais cohérente qui lui permettra de déterminer action
  mener jouer à chaque instant.
  • L’ensemble de l’application sera intégré dans une interface graphique. On pourra utiliser des
  images telles que celles données en annexe.
  • Avant la conception et le développement de l’interface graphique, une interface
  rudimentaire en ligne de commande permettra de tester le moteur du jeu. Cette interface
  devra être conservée lors de l’évaluation fonctionnelle et la remise des fichiers.
  • On intègrera une notion d’extension qui correspondent à des nouvelles cartes, qu’il vous
  faudra concevoir et implémenter. En début de partie, il sera demandé au joueur s’il souhaite
  intégrer ces cartes d’extension ou pas à la partie qui va débuter.
  • On intègrera enfin une notion de variantes dans les règles du jeu. En début de partie, le
  choix d’une variante sera proposé au joueur et c’est la variante choisie qui imposera les
  règles d’une partie. Il est demandé de concevoir et implémenter deux variantes en plus des
  règles de base.
  • L'architecture retenue devra veiller au respect des règles de la conception orientée objet.
  L'architecture devra ainsi être (1) modulaire en identifiant des composants indépendants liés
  entre eux par des relations (2) extensible en permettant le changement de règles exposé ciavant
  ou l'ajout de cartes avec de nouvelles fonctions.
2. Phases et jalons
  Le projet sera découpé en trois phases qui conduiront à des soutenances effectuées durant une
  séance de TP dédiée. Les trois phases sont du projet sont :
   1. la modélisation UML initiale ;
   2. le développement du moteur du jeu, utilisable en lignes de commandes ;
   3. le développement de l’interface graphique et la remise du code source documenté.
  Pour chaque phase et jalon associé, les consignes à suivre vous sont données ci-après.
 2.1. Phase 1 : modélisation UML
 La modélisation UML proposée sera exposée sous la forme d’une présentation qui devra suivre la
 structure suivante :
  1. Introduction : présentation du projet tel que vous l’avez compris (pas de copier/coller de
  l’énoncé) et annonce du plan du document
  2. Diagramme de cas d’utilisation : identifier l’ensemble des cas d’utilisation du système, leurs
  relations et les expliquer.
  3. Diagramme de classes : décrire et expliquer chaque élément de conception du diagramme.
  Par ailleurs, vous indiquerez l’étude que vous aurez faite sur le patron de conception
  Strategy, utilisé en autres, pour les joueurs virtuels, et la manière dont vous l'intégrez dans
  votre modélisation. Vous noterez qu'il est inutile de modéliser les interfaces de commande
  du jeu (en lignes de commandes ou graphique) ; on ne représentera que le cœur de
  l'application.
  4. Diagramme de séquence : Proposer un diagramme de séquence pour le déroulement d'un
  tour de jeu.
  5. Conclusion : Identifier les aspects sûrs de la modélisation et ceux dont vous pensez que le
  développement pourrait induire une modification.
 2.2. Phase 2 : cœur de l'application et interface en lignes de commandes
  Cette phase, validée par une seconde soutenance orale, consistera à faire une première
  démonstration du développement. A cette étape, le moteur du jeu devra être fonctionnel et utilisable
  en lignes de commandes. Mais aucune mise en forme particulière n'est demandée (mise en
  packages, documentation, mise au propre du code, …). De même, aucun support de présentation ne
  devra être préparé pour cette seconde soutenance.
 2.3. Phase 3 : projet complet et documentation
  Cette dernière phase consistera à mettre en œuvre la version finale du projet ainsi que toute la
  documentation qui l'accompagne. D'un point de vue du travail de développement, il consistera à
  intégrer une interface graphique au cœur de l'application développé précédemment. Attention, le jeu
  devra rester jouable à la fois en ligne de commandes et par le biais de l'interface graphique, au sein
  d’une même partie. Pour ce faire, vous devrez mettre en œuvre le patron de conception MVC, vu en
  cours en considérant que l'application possède deux vues concurrentes.
  Une soutenance orale permettra de valider les aspects fonctionnels de l’application et une remise
  des fichiers du projet permettra d’évaluer le code, son organisation, sa structure et sa
  documentation.
  
Evaluation fonctionnelle

 Elle sera effectuée par le biais d’une soutenance qui aura lieu durant votre dernière séance de TP.
 Elle s’effectue par binôme et dure 15 minutes durant lesquelles vous présenterez :
  • L’état fonctionnel de votre application par le biais d'une démonstration. Une fois la
  démonstration passée, tout développement supplémentaire (correction de bugs, ajout de
  fonctionnalités, …) est inutile car l'état fonctionnel de votre projet sera évalué à ce moment.
  Suite à cela, quelques questions relatives au code que vous avez produit vous seront posées.
  • Une présentation qui indiquera :
   1. Le diagramme de classes final. On détaillera ici et justifiera les changements entre la
   version initiale du diagramme de classes et la version finale, effectivement implémentée
   dans le code. Pour ce faire, on présentera les deux diagrammes.
   2. L’état actuel de l’application : Cette partie donnera de manière précise l’état de
   l’application en regard du présent cahier des charges. On y indiquera clairement ce qui a
   été implanté et ce qu’il ne l’est pas, ce qui fonctionne et ce qui reste buggé.
   Remarque : Pour cette évaluation fonctionnelle, il ne vous est pas demandé de mettre au propre le
   code présenté (respect des conventions d'écriture, commentaire Javadoc, suppression des blocs de
   codes inutiles mis en commentaires, …). Par contre, cela devra être fait pour la remise des fichiers.

Remise des fichiers du projet

 Les fichiers du projet à remettre seront déposés sur Moodle, dans un espace dédié à cet effet, et
 placés dans une archive ZIP dont le nom du fichier doit suivre la convention de nommage suivante :
 <Nom1>_<Nom2>_ProjetLO02_A18.ZIP, où <Nom1> et <Nom2> sont données par ordre
 alphabétique. Par exemple : Doyen_Langeron_ ProjetLO02_A18.PDF
 L’archive devra comporter les dossiers suivants :
  - src : contiendra l’ensemble des sources du projet ;
  - dist : contiendra un fichier nommé projet.jar qui contiendra une archive JAR exécutable
  du projet. C’est ce fichier qui sera exécuté pour tester si besoin votre projet après la
  soutenance ;
  - classes : contiendra l’ensemble des classes compilées du projet ;
  - doc : contiendra la javadoc du projet.
 Enfin, les consignes à observer pour la mise en forme du code développé dans le cadre du projet
 sont les suivantes :
  - L’ensemble du code doit être documenté par le biais de commentaires javadoc. Il ne faudra
  pas se contenter des balises standard mais décrire précisément la fonction de chaque élément
  de code (classe, méthode, …) en respectant les conventions de documentation pour la
  production logicielle telles que vues durant le semestre.
  - Le code devra être propre et suivre les conventions d’écriture spécifiées par Oracle
  (nommage, indentation, blocs, casse, …). Aucun code obsolète, placé dans un commentaire,
  ne devra figurer dans les fichiers sources.
3. Consignes générales
 3.1. Calendrier
  Semaine Evénement
  2 (17/09) Distribution des projets et constitution des binômes
  6, 7 et 9 (15/10, 22/10 et 5/11) Soutenance Jalon 1
  12 et 13 (26/11 et 3/12) Soutenance Jalon 2
  18 (7/01) Soutenance
  19 (vendredi 18/01 à 23:55) Remise des fichiers
 3.2. Respect des droits d'auteurs
  Le présent projet consiste en la modélisation et le développement logiciel d'un jeu dont les
  conditions d’utilisation sont spécifiées ici : http://www.goodlittlegames.co.uk/about-faq.html.
  L'Université de Technologie de Troyes n'utilise cette œuvre que dans seul un cadre d'enseignement
  et à des fins exclusivement pédagogiques. En effectuant ce travail au sein d'une unité
  d'enseignement, l'étudiant s'engage à respecter les droits d’auteurs et d’exploitation liés à tout
  support de stockage et de diffusion numérique du code produit dans le cadre de son projet et à
  respecter le cadre exclusivement pédagogique associé à cette production. Toute autre forme
  d'exploitation et de diffusion par un étudiant relève de sa seule responsabilité individuelle.
 3.3. Autres consignes
  - Pour l’ensemble des livrables du projet, aucun retard ne sera accepté et aucune raison ne
  pourra le justifier.
  - Vos intervenants de TP sont vos interlocuteurs exclusifs pour toute question relative au
  projet.
