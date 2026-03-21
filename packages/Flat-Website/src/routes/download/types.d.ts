import dayjs from 'dayjs';

export interface GitHubRelease {
  name: string;
  html_url: string;
  created_at: string;
  assets: Asset[];
}

export interface Asset {
  browser_download_url: string;
  name: string;
  size: number;
}

export interface OsRelease {
  assets: OsAsset[];
  version: string;
  url: string;
  releaseNotesUrl?: string;
  createdAt: dayjs.Dayjs;
}

export interface OsAsset {
  name: string;
  assetName: string;
  assetMatcher: (assetName: string) => boolean;
  asset?: Asset;
  showMoreFormats: boolean;
  otherFormats: Asset[];
}
