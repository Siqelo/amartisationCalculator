import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'za.co.wird.amortisation',
  appName: 'Amortisation Calculator',
  webDir: 'www',
  server: {
    androidScheme: 'https'
  }
};

export default config;
