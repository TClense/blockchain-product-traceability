@echo off
chcp 65001 >nul
title TraceChain - Extract Screenshots from Word Documents

echo ============================================================
echo   TraceChain - Screenshot Extraction Tool
echo   Extracts real images from experiment .docx files
echo ============================================================
echo.

:: Check for required tools
where python >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] Python found - running extraction script...
    python scripts\extract-docx-images.py
    goto :end
)

where node >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] Node.js found - running extraction script...
    node scripts\extract-images-from-docx.js
    goto :end
)

where powershell >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] PowerShell found - running extraction...
    powershell -Command "
        Add-Type -AssemblyName System.IO.Compression.FileSystem;
        $docs = @('experiment4.docx', 'experiment5.docx', 'experiment6.docx');
        $outDir = 'screenshots\real';
        New-Item -ItemType Directory -Force -Path $outDir | Out-Null;
        $count = 0;
        foreach ($doc in $docs) {
            $path = Join-Path 'docs' $doc;
            if (Test-Path $path) {
                Write-Host ('Processing: ' + $doc);
                $tempDir = Join-Path $env:TEMP ('docx_' + [System.IO.Path]::GetFileNameWithoutExtension($doc));
                if (Test-Path $tempDir) { Remove-Item -Recurse -Force $tempDir }
                [System.IO.Compression.ZipFile]::ExtractToDirectory((Resolve-Path $path).Path, $tempDir);
                $mediaDir = Join-Path $tempDir 'word\media';
                if (Test-Path $mediaDir) {
                    $images = Get-ChildItem $mediaDir -Include *.png,*.jpg,*.jpeg,*.gif -Recurse;
                    $i = 1;
                    foreach ($img in $images) {
                        $prefix = [System.IO.Path]::GetFileNameWithoutExtension($doc);
                        $newName = ('{0}-image{1}{2}' -f $prefix, $i, $img.Extension);
                        Copy-Item $img.FullName (Join-Path $outDir $newName);
                        Write-Host ('  -> Saved: ' + $newName);
                        $i++; $count++;
                    }
                }
                Remove-Item -Recurse -Force $tempDir;
            }
        }
        Write-Host ('Extracted ' + $count + ' images to: ' + $outDir);
    "
    goto :end
)

echo [✗] No extraction tool found (Python, Node.js, or PowerShell required)
echo.
echo Please install one of the following:
echo   - Python: https://python.org
echo   - Node.js: https://nodejs.org
echo   - PowerShell: Built-in on Windows
echo.
echo Or manually extract by renaming .docx to .zip and opening word/media/
echo.

:end
echo.
echo Done! Press any key to exit.
pause >nul
