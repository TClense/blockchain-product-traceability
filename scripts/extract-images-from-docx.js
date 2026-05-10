/**
 * Extract images from .docx Word documents
 * 
 * Run: node scripts/extract-images-from-docx.js
 * 
 * .docx files are ZIP archives containing:
 *   - word/media/  (image files)
 *   - word/document.xml  (main content)
 */

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');
const os = require('os');

const DOCS_DIR = path.join(__dirname, '..', 'docs');
const OUTPUT_DIR = path.join(__dirname, '..', 'screenshots', 'real');

// Mapping rules from docx file to screenshot names
const FILE_MAP = [
  { doc: 'experiment4.docx', prefix: 'exp4' },
  { doc: 'experiment5.docx', prefix: 'exp5' },
  { doc: 'experiment6.docx', prefix: 'exp6' }
];

function extractImages() {
  console.log('=== Extracting Images from Word Documents ===\n');
  
  if (!fs.existsSync(DOCS_DIR)) {
    console.error('ERROR: docs/ directory not found!');
    process.exit(1);
  }
  
  if (!fs.existsSync(OUTPUT_DIR)) {
    fs.mkdirSync(OUTPUT_DIR, { recursive: true });
  }

  const files = fs.readdirSync(DOCS_DIR).filter(f => f.endsWith('.docx'));
  console.log(`Found ${files.length} Word documents:\n`);

  let totalImages = 0;

  for (const file of files) {
    const docxPath = path.join(DOCS_DIR, file);
    const stats = fs.statSync(docxPath);
    console.log(`  ${file} (${(stats.size / 1024).toFixed(1)} KB)`);
    
    // Try to find unzip command
    let unzipCmd = '';
    if (os.platform() === 'win32') {
      // Windows - try tar or 7z
      unzipCmd = `tar -xf "${docxPath}" -C "${OUTPUT_DIR}" word/media/ 2>nul || ` +
                 `powershell -Command "Add-Type -Assembly 'System.IO.Compression.FileSystem'; ` +
                 `[System.IO.Compression.ZipFile]::ExtractToDirectory('${docxPath}', '${path.join(OUTPUT_DIR, '_temp_' + file)}')" 2>nul`;
    } else {
      unzipCmd = `unzip -o "${docxPath}" "word/media/*" -d "${OUTPUT_DIR}" 2>/dev/null`;
    }
    
    try {
      execSync(unzipCmd, { stdio: 'pipe' });
      
      // Move extracted media files
      const mediaDir = path.join(OUTPUT_DIR, 'word', 'media');
      if (fs.existsSync(mediaDir)) {
        const mediaFiles = fs.readdirSync(mediaDir);
        for (const mediaFile of mediaFiles) {
          const ext = path.extname(mediaFile).toLowerCase();
          if (['.png', '.jpg', '.jpeg', '.gif'].includes(ext)) {
            const prefix = FILE_MAP.find(f => f.doc === file)?.prefix || 'unknown';
            const newName = `${prefix}-${mediaFile}`;
            fs.renameSync(
              path.join(mediaDir, mediaFile),
              path.join(OUTPUT_DIR, newName)
            );
            console.log(`    → Extracted: ${newName}`);
            totalImages++;
          }
        }
        // Clean up
        fs.rmSync(path.join(OUTPUT_DIR, 'word'), { recursive: true, force: true });
      }
      
      // Clean up temp
      const tempDir = path.join(OUTPUT_DIR, '_temp_' + file);
      if (fs.existsSync(tempDir)) {
        const tempMediaDir = path.join(tempDir, 'word', 'media');
        if (fs.existsSync(tempMediaDir)) {
          const tempFiles = fs.readdirSync(tempMediaDir);
          for (const tf of tempFiles) {
            const ext = path.extname(tf).toLowerCase();
            if (['.png', '.jpg', '.jpeg', '.gif'].includes(ext)) {
              const prefix = FILE_MAP.find(f => f.doc === file)?.prefix || 'unknown';
              const newName = `${prefix}-${tf}`;
              fs.renameSync(
                path.join(tempMediaDir, tf),
                path.join(OUTPUT_DIR, newName)
              );
              console.log(`    → Extracted: ${newName}`);
              totalImages++;
            }
          }
        }
        fs.rmSync(tempDir, { recursive: true, force: true });
      }
    } catch (err) {
      console.log(`    ⚠ Could not auto-extract from ${file}: ${err.message}`);
      console.log(`      To manually extract: unzip "${docxPath}" -d /tmp/docx_extract`);
    }
  }
  
  console.log(`\n=== Done! Extracted ${totalImages} images to: ${OUTPUT_DIR} ===`);
  console.log('Run this script on a machine with Node.js to extract real screenshots.');
}

extractImages();
