<?php
header("Content-Type: application/json; charset=UTF-8");

$servername = "localhost";
$username = "prj-prism-rcastro";
$password = "IXkOmlUeiR8923oa";
$dbname = "prj-prism-rcastro";


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Récupération des gets et activation des fonctions liés 
if($_GET['action'] === "getAllToilettes"){
  getAllToilettes($conn);
}else if($_GET['action'] === "getToiletteById"){
  getToiletteById($conn, $_GET['id']);
}else if($_GET['action'] === "addToilette"){
  addToilette($conn, $_GET['coordinatesX'], $_GET['coordinatesY'], $_GET['adresse'], $_GET['type'], $_GET['horaires'], $_GET['acces_pmr'], $_GET['relais_bebe'], $_GET['free']);
}else{
  echo"<html>";
  echo"<h1>Récupérer tout les toilettes</h1>";
  echo"<p>https://projets.iut-orsay.fr/prj-prism-rcastro/apiTOILETTES.php?action=getAllToilettes</p>";

  echo"<h1>Récupérer un toilette via son id</h1>";
  echo"<p>https://projets.iut-orsay.fr/prj-prism-rcastro/apiTOILETTES.php?action=getToiletteById&id={inserer_id_voulu}</p>";

  echo"<h1>Ajouter un toilette par un utilisateur</h1>";
  echo"<p>https://projets.iut-orsay.fr/prj-prism-rcastro/apiTOILETTES.php?action=addToilette&coordinatesX={float}&coordinatesY={float}&adresse={string}&type={string}&horaires={string}&acces_pmr={boolean}&relais_bebe={boolean}&free={boolean} </p>";
  echo"</html>";
}

// Fonction pour récupérer tout les toilettes au lancement de l'app
function getAllToilettes($conn){
  $sql = "SELECT * FROM WC";
  $result = $conn->query($sql);

  if ($result->num_rows > 0) {
    // output data of each row
    $array = [];
    while($row = $result->fetch_assoc()) {
      $array[] = $row;
    }

    echo json_encode($array);
  } else {
    echo "0 results";
  }
  $conn->close();
}

// Fonction pour récupérer toutes les infos d'un toilette par son ID
function getToiletteById($conn, $id){
  $sql = "SELECT * FROM WC WHERE id = $id";
  $result = $conn->query($sql);

  if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      echo json_encode($row);
    }
  } else {
    echo "0 result";
  }
  $conn->close();
}

// Fonction pour ajouter un toilette
function addToilette($conn, $coordinatesX, $coordinatesY, $adresse, $type, $horaires, $acces_pmr, $relais_bebe, $free){
$adresse = addslashes($adresse);
  $sql = "INSERT INTO WC(datasetid, coordinatesX, coordinatesY, adresse, type, horaires, acces_pmr, relais_bebe, free) VALUES ('ajoutUtilisateur', $coordinatesX, $coordinatesY, '$adresse', '$type', '$horaires', $acces_pmr, $relais_bebe, $free)";

  if ($conn->query($sql) === TRUE) {
    //echo json_encode(["code" => 200, "body" => "Ajout d'une toilette r�ussi"]);
	echo json_encode(["code" => 200]);
    } else {
    echo json_encode(array("message" => "Echec de l'ajout du toilette. Error: " . $conn->error));
}
$conn->close();
}
?> 