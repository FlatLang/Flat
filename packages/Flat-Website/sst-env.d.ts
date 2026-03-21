/* tslint:disable */
/* eslint-disable */
import "sst"
declare module "sst" {
  export interface Resource {
    "GitHubApiToken": {
      "type": "sst.sst.Secret"
      "value": string
    }
    "cache": {
      "name": string
      "type": "sst.aws.Dynamo"
    }
    "flatlang": {
      "type": "sst.aws.SvelteKit"
      "url": string
    }
  }
}
export {}
