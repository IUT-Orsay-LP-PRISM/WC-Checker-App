<?php

header("Content-Type: application/json; charset=UTF-8");

include_once 'infos.php';

// Fonction pour récupérer toutes les toilettes au lancement de l'app
function getAllToilettes($conn)
{
    $sql = "SELECT * FROM WC";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // output data of each row
        $array = [];
        while ($row = $result->fetch_assoc()) {
            $array[] = $row;
        }

        echo json_encode($array);
    } else {
        echo "0 results";
    }
    $conn->close();
}

// Fonction pour récupérer toutes les infos d'un toilette par son ID
function getToiletteById($conn, $id)
{
    $sql = "SELECT * FROM WC WHERE id = $id";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // output data of each row
        while ($row = $result->fetch_assoc()) {
            echo json_encode($row);
        }
    } else {
        echo "0 result";
    }
    $conn->close();
}

// Fonction pour ajouter un toilette
function addToilette($conn, $coordinatesX, $coordinatesY, $adresse, $type, $horaires, $acces_pmr, $relais_bebe, $free)
{
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
