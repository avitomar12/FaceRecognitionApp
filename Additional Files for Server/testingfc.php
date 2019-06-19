<?php
    $json = json_decode(file_get_contents('php://input'),true);
 
    $name = $json["name"]; //within square bracket should be same as Utils.imageNametils.image
    $image = $json["image"];
 

 
    $decodedImage = base64_decode("$image");
 
    $return = file_put_contents("uploads/".$name.".jpg", $decodedImage);
 
    if($return !== false){
        $response->success = 1;
        $response->message = "Image Uploaded Successfully";
	$response->result=shell_exec("python3 /home/ubuntu/facerecognize_second.py");
    }else{
        $response->success = 0;
        $response->message = "Image Uploaded Failed";
	$response->result="Not Executed";

    }
 
	echo json_encode($response);
?>



