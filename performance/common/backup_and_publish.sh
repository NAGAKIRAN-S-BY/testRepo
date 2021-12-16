#!/bin/bash
set -e
# Script to backup performance database to bacpac file and upload to storage account
if [[ -z "$STORAGE_ACCESS_KEY" ]]; then
    echo "Must provide STORAGE_ACCESS_KEY in environment"
    exit 1
fi
if [[ -z "$DB_NAME" ]]; then
    echo "Must provide DB_NAME in environment"
    exit 1
fi

# install all needed dependencies
apt-get update && apt-get install unzip curl -y
curl -sL https://aka.ms/InstallAzureCLIDeb | bash
wget -progress=bar:force -q -O sqlpackage.zip https://go.microsoft.com/fwlink/?linkid=2134311 && unzip -qq sqlpackage.zip -d /opt/sqlpackage && chmod +x /opt/sqlpackage/sqlpackage

# Rebuild all indexes and update all statistics
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P P@ssword01 -d $DB_NAME -Q "Exec sp_msforeachtable 'ALTER INDEX ALL ON ? REBUILD'; Exec sp_updatestats"

# backup database and upload to Azure storage account
/opt/sqlpackage/sqlpackage /a:Export /ssn:. /sdn:$DB_NAME /su:sa /sp:P@ssword01 /tf:/tmp/$DB_NAME.bacpac
az storage blob upload --account-name ecomblobstorage --account-key "$STORAGE_ACCESS_KEY" --container-name performance --file /tmp/$DB_NAME.bacpac --name $DB_NAME.bacpac