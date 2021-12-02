<?php

// specify your API token here
$apiKey = "your-api-key";

// specify your URL here
$url = 'https://api.mrcmekong.org/v1/time-series/location?countryCode=LA';

// specify your CSV file name and location here
$csvFile = '/var/www/html/public/csv-export.csv';

$responseArray = $this->apiCall($apiKey, $url);

$this->jsonToCSV($responseArray, $csvFile);

echo 'CSV export complete';


function apiCall($apiKey, $url)
{
    // Create a stream
    $opts = [
        "http" => [
            "method" => "GET",
            "header" => "accept: application/json\r\n" .
                "X-API-Key: " . $apiKey . "\r\n"
        ]
    ];

    // DOCS: https://www.php.net/manual/en/function.stream-context-create.php
    $context = stream_context_create($opts);

    // Open the file using the HTTP headers set above
    // DOCS: https://www.php.net/manual/en/function.file-get-contents.php
    $response = file_get_contents($url, false, $context);
    return json_decode($response, true);
}

function jsonToCSV($data, $cfilename)
{
    $fp = fopen($cfilename, 'w');
    $header = false;
    foreach ($data as $row) {
        if (empty($header)) {
            $header = array_keys($row);
            fputcsv($fp, $header);
            $header = array_flip($header);
        }
        fputcsv($fp, array_merge($header, $row));
    }
    fclose($fp);
    return;
}
