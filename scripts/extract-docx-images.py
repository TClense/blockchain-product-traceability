#!/usr/bin/env python3
"""
Extract real screenshots from Word (.docx) documents.
Usage: python scripts/extract-docx-images.py

This script extracts embedded images from experiment .docx files
and saves them to screenshots/real/ with descriptive names.
"""

import zipfile
import os
import re
import argparse
from pathlib import Path

# Configuration
DOCS_DIR = Path(__file__).resolve().parent.parent / 'docs'
OUTPUT_DIR = Path(__file__).resolve().parent.parent / 'screenshots' / 'real'
PROJECT_ROOT = Path(__file__).resolve().parent.parent

# Document image extraction mapping
DOCX_FILES = [
    'experiment4.docx',
    'experiment5.docx',
    'experiment6.docx',
]

# Descriptive naming based on content analysis
IMAGE_RENAMES = {
    # Pattern: (original_name_pattern, descriptive_name)
    'image1': '01-producer-page',
    'image2': '02-supplier-page',
    'image3': '03-retailer-page',
    'image4': '04-trace-query-page',
    'image5': '05-trace-detail-page',
    'image6': '06-webase-deploy',
    'image7': '07-contract-deploy',
    'image8': '08-idea-backend',
    'image9': '09-vue-frontend',
    'image10': '10-application-config',
    'image11': '11-solidity-contract',
    'image12': '12-database-config',
}


def extract_images_from_docx(docx_path: Path, output_dir: Path) -> list:
    """Extract all images from a .docx file."""
    extracted = []
    try:
        with zipfile.ZipFile(docx_path, 'r') as zip_ref:
            # List all files in the ZIP
            all_files = zip_ref.namelist()
            
            # Get media files
            media_files = [f for f in all_files if f.startswith('word/media/')]
            
            if not media_files:
                print(f"  ⚠ No media files found in {docx_path.name}")
                return extracted
            
            print(f"\n  📎 {docx_path.name}: Found {len(media_files)} embedded image(s)")
            
            for media_file in media_files:
                # Extract the image data
                image_data = zip_ref.read(media_file)
                
                # Determine original filename
                original_name = Path(media_file).name
                ext = Path(original_name).suffix.lower()
                
                if ext not in ('.png', '.jpg', '.jpeg', '.gif', '.bmp'):
                    print(f"    ⏭ Skipping non-image: {original_name} ({ext})")
                    continue
                
                # Generate descriptive filename
                base_name = Path(original_name).stem.lower()
                # Try to map to a descriptive name
                desc_name = IMAGE_RENAMES.get(base_name, f'image-{len(extracted)+1}')
                
                output_filename = f'{desc_name}{ext}'
                output_path = output_dir / output_filename
                
                # Avoid overwriting
                counter = 1
                while output_path.exists():
                    output_path = output_dir / f'{desc_name}_{counter}{ext}'
                    counter += 1
                
                # Save the image
                with open(output_path, 'wb') as f:
                    f.write(image_data)
                
                file_size = len(image_data) / 1024
                print(f"    ✅ Saved: {output_filename} ({file_size:.1f} KB)")
                extracted.append(output_filename)
                
    except zipfile.BadZipFile:
        print(f"  ❌ Error: {docx_path.name} is not a valid ZIP/docx file")
    except Exception as e:
        print(f"  ❌ Error processing {docx_path.name}: {e}")
    
    return extracted


def main():
    parser = argparse.ArgumentParser(description='Extract images from Word documents')
    parser.add_argument('--source', '-s', help='Specific docx file path (optional)')
    parser.add_argument('--output', '-o', default=str(OUTPUT_DIR), help='Output directory')
    args = parser.parse_args()
    
    output_dir = Path(args.output)
    output_dir.mkdir(parents=True, exist_ok=True)
    
    print("=" * 60)
    print("  📸 Word Document Image Extractor")
    print("=" * 60)
    
    all_extracted = []
    
    if args.source:
        # Extract from a specific file
        source_path = Path(args.source)
        if source_path.exists():
            print(f"\n📄 Processing: {source_path.name}")
            extracted = extract_images_from_docx(source_path, output_dir)
            all_extracted.extend(extracted)
        else:
            print(f"❌ File not found: {source_path}")
    else:
        # Extract from all files
        for docx_name in DOCX_FILES:
            docx_path = DOCS_DIR / docx_name
            if docx_path.exists():
                print(f"\n📄 Processing: {docx_name}")
                extracted = extract_images_from_docx(docx_path, output_dir)
                all_extracted.extend(extracted)
            else:
                print(f"\n⚠ File not found: {docx_name}")
    
    print(f"\n{'=' * 60}")
    print(f"  ✅ Extracted {len(all_extracted)} images to: {output_dir}")
    print(f"{'=' * 60}")
    
    if all_extracted:
        print("\n📋 Extracted files:")
        for f in sorted(all_extracted):
            print(f"  • {f}")
    
    print("\n💡 Next step: Run the README update script to reference these images.")


if __name__ == '__main__':
    main()
