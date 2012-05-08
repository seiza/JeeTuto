Projet tutorial visant à mettre en oeuvre les technologies Java EE.

http://en.wikipedia.org/wiki/Markdown

Utilisation :

* Obtenir les sources depuis un terminal : `git clone https://github.com/seiza/JeeTuto.git`
* Lancer IntelliJ (http://www.jetbrains.com/idea/download/index.html), faire "Ouvrir un projet" et sélectionner le `pom.xml` racine
* Copier le fichier `settings.xml` (à la racine de ce projet) dans votre répertoire `~/.m2`
    * Exécuter `mvn clean package` dans un terminal (copie l'EAR dans le répertoire `/usr/local/jboss-6.1.0.Final/server/default/deploy/` si il existe !)
* Dans IntelliJ ajouter la librairie `/usr/local/jboss-6.1.0.Final/client/jbossall-client.jar` au module `jeetuto-client`
    * Puis exécuter les classes client `ClientHelloSessionStateless` (trace dans la console IntelliJ) ou `ClientHelloJMSProducer` (trace dans la console de JBoss)

Pour créer une nouvelle version : `mvn release:prepare`



# Versions

Versions (tags GitHub) :

* `jeetuto-0.1` : Projet JEE minimaliste avec uniquement un EJB Stateless et un appel depuis une classe Java simple.
* `JeeTuto-0.2` : Ajout d'un Message Driven Bean.
* `JeeTuto-0.3.1` : Ajout d'un Entity Bean (JPA / Hibernate).




## Release 0.3.1

### Contenu

1) Utilisation d'une base de données dans la persistance (JPA / Hibernate) :

* Création d'une branche Git sur le tag `JeeTuto-0.3` et upgrade de version à `JeeTuto-0.3.1` pour travailler.
* [Configuration de la base de données HSQLDB intégrée à JBoss AS](http://agora.2ia.net/mediawiki/index.php?title=HSQLDB#Version_int.C3.A9gr.C3.A9e_.C3.A0_JBoss_Application_Server) pour ajouter le DataSource `JeeTutoDS` :
    * Création du fichiers projet `<jeetuto-ejb>/META-INF/jeetuto-ds.xml` pour définir le `local-tx-datasource` (plutôt que dans le fichier `JBOSS_HOME/server/default/deploy/hsqldb-ds.xml`)
    * Création du fichiers projet `<jeetuto-ejb>/META-INF/jeetuto-jboss-beans.xml` pour définir le `application-policy` (plutôt que dans le fichier `JBOSS_HOME/server/default/conf/login-config.xml`)
* Refactoring du fichier `<jeetuto-ejb>/META-INF/persistence.xml` (nommage correct du persistence unit et correction de sa configuration)
* Ajout de la configuration suivante au `pom.xml` du module `jeetuto-ejb` :

    <!-- Dans la balise project/plugins/plugin[artifactId="maven-ejb-plugin"]/configuration -->
    <archive>
        <manifest>
            <addClasspath>true</addClasspath>
        </manifest>
    </archive>

* Suppression du fichier `<jeetuto-model>/META-INF/persistence.xml` qui existait à tord et créait des conflit avec celui du module `jeetuto-ejb`.
* Configuration du plugin Maven `hibernate3-maven-plugin` dans le `pom.xml` du module `jeetuto-model` afin de pouvoir générer le schéma de base de données
* Correction de la dépendance `hibernate-entitymanager` dans le `pom.xml` du module `jeetuto-model` :
    * Upgrade de la version utilisée à `3.4.0.GA` pour supprimer des erreurs au runtime liées à des conflits de version sur les dépendances transitives
    * Changement du `scope` à `provided` également pour éviter des erreurs au runtime liées à des collisions dans les libs utilisées.
* Ajout de la dépendance à `dom4j` dans le `pom.xml` du module `jeetuto-ear`.


2) Création d'un EJB Stateless

* Ajout des annotations `@Stateless` sur la classe `AddressBookBean`
* Ajout des annotations `@PersistenceContext(unitName="JeeTutoPU")` sur l'attribut membre `AddressBookBean.entityManager` afin de permettre l'injection sans equivoque.


3) Création du client

* Création du package `me.couvreur.java.jeetuto.client.entity` dans le module `jeetuto-client`
* Création de la classe `ClientAddressBookStateless` dedans pour appeler l'EJB Stateless et lui transmettre l'EJB Entity `Person` créé pour le persister.



### Remarques


## Release 0.3

### Contenu

Ajout de la persistance (JPA / Hibernate) :

* Entity Beans `Person` vers `Address` dans le module `jeetuto-model` pour isoler le modèle et la persistance (plus facile pour tester).
    * Dans le package `me.couvreur.java.jeetuto.ejb.entity.model`.
* Tests unitaires (`PersonTest` et `AddressTest`) :
    * Voir http://ejb3unit.sourceforge.net/Session-Bean.html
* Relation OneToMany de `Person` vers `Address` (voir l'annotation au dessus de la méthode `Person.getAdresses()` pour la configuration clef).
* L'EJB Session Stateless `AddressBookBean` a été créé pour pouvoir accéder aux Entity Beans, mais il n'est terminé car aucune base n'est installée / configurée.
    * Voir http://www.eclipsetotale.com/articles/Introduction_EJB3_avec_Eclipse.html
* Configuration Maven pour automatiquement copier l'EAR dans le répertoire `/usr/local/jboss-6.1.0.Final/server/default/deploy/`



### Remarques

* `Person` et `Address` (les objets persistés) doivent implémenter `Serializable` (si utilisés comme Entity Bean et pas seulement serialisés).



## Release 0.2

### Contenu

Ajout d'un Message Driven Bean (MDB / JMS) :

* Dans le package `me.couvreur.java.jeetuto.ejb.mdb`.
* Le Message Producer et le MDB fonctionnent avec une Queue.
* Mais impossible de faire fonctionner le Messager/Queue Receiver ;(
* Mise en place de la livraison via Maven : `mvn mvn release:prepare` et 3 fois 'return' (utiliser les 3 propositions par défaut)


### Remarques

* Attention, dans JBoss, toutes les destinations de type Queue doivent commencer par `queue/` (voir l'annotation `@MessageDriven/@ActivationConfigProperty` dans la classe `me.couvreur.java.jeetuto.ejb.mdb.HelloMessageListenerMDB`)
* Attention, dans JBoss, la déclaration des queues se fait dans un fichier `hornetq-jms.xml` (et non pas `jms.xml`) dans le répertoire ressource `META-INF` du module `jeetuto-ejb`
* Pour la mise à jour du numéro de version, la prochaine fois utiliser `mvn release:update-versions -DautoVersionSubmodules=true` lorsqu'une version est finie (le faire en fin de version 0.2 -> 0.3)



## Release 0.1

### Contenu

Le minimum pour mettre en oeuvre un EJB :

* Un EJB session stateless (qui prend un paramètre String retourne une String concaténée)
* Un EAR encapsulant l'EJB à des fins de déploiement
* Un client qui se contente d'appeler l'EJB, de l'appeler et de logger le résultat (aucune autre technologie)

Il n'y a pas encore de JMS / MDB, de persistance ou d'application web.


### Environnement technique

* JBoss AS 6
    * J'ai initialement commencé avec JBoss AS 7, mais trop de limitations et de bugs pour débuter
    * Voir [Installer JBoss AS 7 sur Mac](http://www.agora.2ia.net/mediawiki/index.php?title=JBossPLP#JBoss_7_AS_Sous_MAC_OS_X) (la procédure reste la même que pour la v6).
* Maven 3 
    * Voir [Installer Maven3 sur Mac](http://www.agora.2ia.net/mediawiki/index.php?title=Maven3#Mac_OS_X)
* IntelliJ 11 :
    * [Use Maven in Intellij IDEA](https://wiki.openmrs.org/display/docs/Use+Maven+In+Intellij+IDEA)
    * [Maven IDEA Plugin](http://maven.apache.org/plugins/maven-idea-plugin/index.html)

Voir aussi :

* Le paragraphe "Ecrire le projet 'from scratch'" pour plus de détails
* [la page JavaEE sur mon wiki](http://www.agora.2ia.net/mediawiki/index.php?title=JavaEE) pour les détails d'installation.


### Configurations de l'environnement

* Récupérer les sources : Faire un `git clone git@github.com:seiza/JeeTuto.git`
* Copier le fichier `settings.xml` (à la racine de ce projet) dans votre répertoire `~/.m2`
* Dans IntelliJ (ou votre IDE favoris) ajouter au module jeetuto-client la librairie `/usr/local/jboss-6.1.0.Final/client/jbossall-client.jar`


### Construction du projet

* Depuis une console, lancer la commande `mvn package` pour construire les artefacts
* Déployer le EAR de `jeetuto/target/jeetuto-ear-1.0.ear` dans le conteneur EJB (JBoss 6, répertoire)
* Exécuter la classe `me.couvreur.java.jeetuto.client.session.ClientHelloSessionStateless` du module `jeetuto-client` : vous devrier voir une trace interessante ;)


Attention :

* Il va peut être falloir changer le port JNDI dans le fichier `jeetuto-swing/src/main/resources/jndi.properties` pour que cela fonctionne.



# Ecrire le projet "from scratch"

## Module racine dans Maven

    mkdir ejb-gwt
    cd ejb-gwt
    gedit pom.xml


## Sous-module EJB

    mvn archetype:create -DgroupId=me.couvreur.java -DartifactId=ejb-jar
    cd ejb-jar/src/main
    mkdir resources
    cd resources
    mkdir META-INF
    cd META-INF
    mate persistence.xml


## Configuration de Maven pour JBoss AS 7 (fichier settings.xml)

Ajouter dans le settings.xml :

    <profiles>
          <profile>
        <id>jboss-public-repository</id>
        <repositories>
          <repository>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Maven Repository Group</name>
            <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
            <layout>default</layout>
            <releases>
              <enabled>true</enabled>
              <updatePolicy>never</updatePolicy>
            </releases>
            <snapshots>
              <enabled>true</enabled>
              <updatePolicy>never</updatePolicy>
            </snapshots>
          </repository>
        </repositories>
        <pluginRepositories>
          <pluginRepository>
            <id>jboss-public-repository-group</id>
            <name>JBoss Public Maven Repository Group</name>
            <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
            <layout>default</layout>
            <releases>
              <enabled>true</enabled>
              <updatePolicy>never</updatePolicy>
            </releases>
            <snapshots>
              <enabled>true</enabled>
              <updatePolicy>never</updatePolicy>
            </snapshots>
          </pluginRepository>
        </pluginRepositories>
      </profile>
    </profiles>


Vous aurez alors l'erreur suivante :

    [ERROR] Failed to execute goal on project ejb-jar: Could not resolve dependencies for project
    me.couvreur.java.jeetuto:ejb-jar:ejb:1.0-SNAPSHOT: Failed to collect dependencies for
    [org.jboss.jbossas:jboss-as-client:pom:6.1.0.Final (provided), junit:junit:jar:4.7 (test)]:
    Failed to read artifact descriptor for trove:trove:jar:2.1.1: Could not transfer artifact
    trove:trove:pom:2.1.1 from/to jboss (http://repository.jboss.org/maven2): Access denied to:
    http://repository.jboss.org/maven2/trove/trove/2.1.1/trove-2.1.1.pom, ReasonPhrase:Forbidden. -> [Help 1]

Ajouter dans le settings.xml :

    <mirrors>
      <mirror>
        <id>deprecated-jboss-repo</id>
        <name>Use new repo to handle requests to deprecated repo</name>
        <mirrorOf>repository.jboss.org</mirrorOf>
        <url>http://repository.jboss.org/nexus/content/repositories/deprecated</url>
      </mirror>
      <mirror>
        <id>deprecated-jboss-repo2</id>
        <name>Use new repo to handle requests to deprecated repo</name>
        <mirrorOf>jboss</mirrorOf>
        <url>http://repository.jboss.org/nexus/content/repositories/deprecated</url>
      </mirror>
    </mirrors>


## Positionner le Module SDK dans IntelliJ pour chaque module

Voir : Capture d’écran 2012-04-15 à 14.18.07.png

Créer le SDK dans IntelliJ : `Project Settings > SDKs > [+]`

Puis le selectionner : `Project Settings > Modules > module-name > Dependencies > Module SDk : 1.6`



## Erreur lors du lookup côté client :

Si vous avez l'erreur :

    Exception in thread "main" java.lang.ClassCastException: javax.naming.Reference cannot be cast to
    me.couvreur.java.jeetuto.ejb.session.HelloSession

Alors il faut ajouter la librairie `jbossall-client.jar` (qui se trouve dans `JBOSS_HOME/client`) au projet IntelliJ `jeetuto-client`.


## Maven properties

* http://docs.codehaus.org/display/MAVENUSER/MavenPropertiesGuide
* http://www.sonatype.com/books/mvnref-book/reference/resource-filtering-sect-properties.html

