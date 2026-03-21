import { exec } from 'node:child_process';

async function getHostedZoneId(domain: string): Promise<string> {
  return new Promise((resolve, reject) => {
    exec(
      `aws route53 list-hosted-zones-by-name --query "HostedZones[?Name=='${domain}.'].Id"  --output text | sed s#/hostedzone/##`,
      (error, stdout, stderr) => {
        if (error) {
          console.error(stderr);
          return reject(error);
        }
        resolve(stdout.trim());
      },
    );
  });
}

function getCustomDomain(hostedZoneId: string) {
  return {
    name: domainName,
    dns: sst.aws.dns({
      zone: hostedZoneId,
    }),
  };
}

const githubApiToken = new sst.Secret('GitHubApiToken');
const domain = process.env.DOMAIN || 'flatlang.org';

if (!domain) throw new Error('Missing DOMAIN environment variable');

const cacheTable = new sst.aws.Dynamo('cache', {
  fields: {
    key: 'string',
  },
  primaryIndex: { hashKey: 'key' },
});

const isProd = $app.stage === 'prod';
const subdomain = isProd ? '' : `${$app.stage}.`;
const domainName = `${subdomain}${domain}`;

const hostedZoneId = await getHostedZoneId(domain);
const customDomain = getCustomDomain(hostedZoneId);

new sst.aws.SvelteKit('flatlang', {
  domain: customDomain,
  environment: {
    GITHUB_API_TOKEN: githubApiToken.value,
    LOGGING_DEFAULT_SHOW_FORMATTING: 'false',
    LOGGING_DEFAULT_SHOW_PREFIX: 'true',
    LOGGING_DEBUG_LABEL_LOGGING_LEVELS: '*',
  },
  link: [cacheTable],
});

export const outputs = {
  host: `https://${domainName}`,
};
