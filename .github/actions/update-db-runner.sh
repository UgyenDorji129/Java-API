#!/bin/bash
echo "Fetching access token......"
export access_token=$(curl -X POST $COGNITO_TOKEN_URI -H "Content-Type: application/x-www-form-urlencoded" -d "client_id=$CLIENT_ID" -d "client_secret=$CLIENT_SECRET" -d "grant_type=client_credentials" | jq -r '.access_token')
echo "access_token:" $access_token
echo "Fetched access token successfully"
echo "deployed url value:" $DEPLOYED_URL
echo "s3 path:" $S3_PATH
if test "$STATUS"
then 
    export apiresponse=$(curl -X POST -v $GQL_URL -H 'Content-Type: application/json' -H "x-api-token:${access_token}" -d '{"query": "mutation($repoName: String!,$s3Path: String,$deployedUrl: String, $serviceName: String, $runId: String,$status: String){ updateUserAssessmentDetails(repoName:$repoName,deployedUrl:$deployedUrl,s3Path:$s3Path,serviceName:$serviceName,runId:$runId,status:$status){ok}} ","variables": {"repoName": "'"${REPO_NAME}"'", "runId": "'"${RUN_ID}"'", "status": "'"${STATUS}"'"}}' )
else
    export apiresponse=$(curl -X POST -v $GQL_URL -H 'Content-Type: application/json' -H "x-api-token:${access_token}" -d '{"query": "mutation($repoName: String!,$s3Path: String,$deployedUrl: String, $serviceName: String, $runId: String,$status: String){ updateUserAssessmentDetails(repoName:$repoName,deployedUrl:$deployedUrl,s3Path:$s3Path,serviceName:$serviceName,runId:$runId,status:$status){ok}} ","variables": {"repoName": "'"${REPO_NAME}"'", "s3Path": "'"${S3_PATH}"'", "serviceName": "'"${SERVICE_NAME}"'", "deployedUrl": "'"${DEPLOYED_URL}"'", "runId": "'"${RUN_ID}"'"}}' )
fi
echo "api response:" $apiresponse