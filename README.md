# 
Working only on WINDOWS

Prerequisite:
Java, Maven, Git, TestNG (install in Eclipse if you want to run the tests from IDE)

Step 1. Open cmd and run next command in the directory where you want to download the code
			git clone https://github.com/gabifericean/apphagfer070380.git

Step 2. Execute next command:  mvn clean install -DskipTests (you should be inside the apphagfer070380 directory)

Step 3. Run traditional tests using next command: mvn test -Dsurefire.suiteXmlFiles=testng_TraditionalTests.xml (you should be inside the apphagfer070380 directory)

Step 4. Run visual AI tests using next command: mvn test -Dsurefire.suiteXmlFiles=testng_VisualAITests.xml (you should be inside the apphagfer070380 directory)

