Projet tutorial visant à mettre en oeuvre les technologies Java EE.

http://en.wikipedia.org/wiki/Markdown


# Versions

Versions (tags GitHub) :

* `jeetuto-0.1` : Projet JEE minimaliste avec uniquement un EJB Stateless et un appel depuis une classe Java simple.
* `jeetuto-0.2` : Ajout d'un Message Driven Bean.


## Release 0.2

### Contenu

Ajout d'un Message Driven Bean (MDB / JMS).
* Dans le package `me.couvreur.java.jeetuto.ejb.mdb`.
* Le Message Producer et le MDB fonctionnent avec une Queue.
* Mais impossible de faire fonctionner le Messager/Queue Receiver ;(



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