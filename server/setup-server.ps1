# Fabric Server Setup Script (Minecraft 1.21.11)
# Run this script from the 'server' folder to set up your test server environment.

$ErrorActionPreference = "Stop"

# Create directories
Write-Host "Creating server directories..."
New-Item -ItemType Directory -Force -Path "mods" | Out-Null
New-Item -ItemType Directory -Force -Path "config" | Out-Null

# Create eula.txt to accept Minecraft EULA
Write-Host "Creating eula.txt..."
Set-Content -Path "eula.txt" -Value "eula=true"

# Download Fabric Server Launcher Jar
# Using Minecraft 1.21.11, Loader 0.19.3, Installer 1.0.1
$fabricServerUrl = "https://meta.fabricmc.net/v2/versions/loader/1.21.11/0.19.3/1.0.1/server/jar"
$fabricServerPath = "fabric-server.jar"

Write-Host "Downloading Fabric Server Launcher from $fabricServerUrl..."
Invoke-WebRequest -Uri $fabricServerUrl -OutFile $fabricServerPath

# Define list of mods to download from Modrinth
$mods = @("fabric-api", "simple-voice-chat", "geyser", "floodgate", "anti-xray", "antifreecam")

foreach ($mod in $mods) {
    Write-Host "----------------------------------------"
    Write-Host "Fetching compatible version for $mod from Modrinth API..."
    $url = "https://api.modrinth.com/v2/project/$mod/version"
    
    try {
        $response = Invoke-RestMethod -Uri $url -Method Get
        
        # Look for 1.21.11 Fabric release first
        $version = $response | Where-Object { $_.game_versions -contains "1.21.11" -and $_.loaders -contains "fabric" } | Select-Object -First 1
        
        if ($null -eq $version) {
            Write-Host "1.21.11 version not found for $mod, attempting fallback to 1.21.x series..."
            $version = $response | Where-Object { 
                ($_.game_versions | Where-Object { $_ -like "1.21*" -or $_ -like "26.1*" }) -and 
                $_.loaders -contains "fabric" 
            } | Select-Object -First 1
        }
        
        if ($version) {
            $file = $version.files[0]
            $destPath = "mods/$($file.filename)"
            Write-Host "Found version: $($version.version_number)"
            Write-Host "Downloading $($file.filename)..."
            Invoke-WebRequest -Uri $file.url -OutFile $destPath
            Write-Host "Successfully downloaded $mod!"
        } else {
            Write-Warning "Could not find a compatible version of $mod for Minecraft 1.21.x series on Fabric."
        }
    } catch {
        Write-Warning "Failed to query or download $($mod) - $($_)"
    }
}

Write-Host "----------------------------------------"
Write-Host "Server setup complete!"
Write-Host "Compile your custom mod using './gradlew build' and copy the resulting jar into the 'server/mods/' folder."
Write-Host "You can start the server using: 'java -Xmx2G -jar fabric-server.jar nogui'"
