<script lang="ts">
  import { onMount } from 'svelte';
  import { jscd } from '$lib/util';

  let os = jscd.os || '';
  let osVersion = jscd.osVersion;
  let lowerOs = os?.toLowerCase();
  let osHeader = os + (osVersion?.trim() ? ' ' + osVersion : '');

  if (lowerOs?.indexOf('mac') == 0) {
    lowerOs = 'mac';
  }

  let oses: any = {
    windows: {
      name: 'Windows',
      key: 'windows',
      extension: '.exe',
      filename: 'FlatInstaller',
      stableVersions: [],
      betaVersions: ['0.3.0', '0.3.3', '0.3.4', '0.3.5', '0.3.6', '0.3.7', '0.3.8'],
    },
    mac: {
      name: 'Mac OS X',
      key: 'mac',
      extension: '.dmg',
      filename: 'Flat',
      stableVersions: [],
      betaVersions: ['0.3.2', '0.3.3', '0.3.4', '0.3.5', '0.3.6', '0.3.7', '0.3.8'],
    },
    linux: {
      name: 'Linux',
      key: 'linux',
      extension: '.deb',
      filename: 'flat',
      stableVersions: [],
      betaVersions: ['0.3.5', '0.3.6', '0.3.7', '0.3.8'],
    },
  };

  let currentOs = oses[lowerOs];

  let osArray = Object.keys(oses).map((key) => {
    return oses[key];
  });

  onMount(() => {
    if (!window.location.hash) {
      window.location.hash = '#' + lowerOs;
    }
  });
</script>

<hr />
<div anchor-button id="windows">
  <h1>Configuring a Windows environment</h1>
  <h3>If you used the installer, then you should already have these configurations set up.</h3>
  {#if lowerOs === 'windows'}
    <h4 class="gray">We think you are running {osHeader}</h4>
  {/if}

  <p>From the start menu, click the settings gear:</p>
  <img
    style="height: 277px;"
    class="dark-background"
    src="/images/windows-step1.png"
    alt="Settings gear"
  />

  <p>
    Next, in the Settings search box, type in "environment variables" and select "Edit the system
    environment variables".
  </p>
  <img
    style="height: 405px;"
    class="dark-background"
    src="/images/windows-step2.png"
    alt="System environment variables"
  />

  <p>
    A "System Properties dialog will pop up. Click the "Environment Variables..." button at the
    bottom.
  </p>
  <img style="height: 485px;" src="/images/windows-step3.png" alt="Environtment variables" />

  <p>On the new dialog, click the "Edit..." button.</p>
  <img style="height: 218.66px;" src="/images/windows-step4.png" alt="Edit environment variables" />

  <p>Next, click the "New" button in the top right corner.</p>
  <img style="height: 426.79px;" src="/images/windows-step5.png" alt="New environment variable" />

  <p>
    After having clicked the "New" button, it will allow you to type the new environment variable
    value. Add the path to the bin folder where flat was installed.
  </p>
  <div class="note">
    If you don't know where it was installed, it was most likely installed to "C:\Program
    Files\Flat\bin" or "C:\Program Files (x86)\Flat\bin"
  </div>
  <img style="height: 433.21px;" src="/images/windows-step6.png" alt="Enter environment variable" />

  <p>Finally, just click OK on all of the open dialogs to save the changes.</p>
  <img
    style="height: 194.12px; margin-right: 5%"
    src="/images/windows-step7.png"
    alt="Save environment variables changes"
  />
  <img
    style="height: 438.73px;"
    src="/images/windows-step8.png"
    alt="Save system properties changes"
  />
  <div class="note">
    After setting the environment variables, you will need to restart any open command prompts or
    IDEs for the changes to take effect.
  </div>
</div>
<hr />
<div anchor-button id="mac">
  <h1>Configuring a Mac OS X environment</h1>
  {#if lowerOs === 'mac'}
    <h4 class="gray">We think you are running {osHeader}</h4>
  {/if}
</div>
<hr />
<div anchor-button id="linux">
  <h1>Configuring a Linux environment</h1>
  {#if lowerOs === 'linux'}
    <h4 class="gray">We think you are running {osHeader}</h4>
  {/if}
</div>
