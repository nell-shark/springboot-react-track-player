import { trackService } from '@services/trackService';

export const audio = new Audio();

export async function playAudioElement(trackId: string) {
  try {
    audio.pause();
    const { data } = await trackService.getTrackById(trackId);
    const binaryString = atob(data.bytes!);
    const uint8Array = new Uint8Array(Array.from(binaryString, char => char.charCodeAt(0)));
    const audioBlob = new Blob([uint8Array], { type: 'audio/mpeg' });
    audio.src = URL.createObjectURL(audioBlob);
    await audio.play();
    URL.revokeObjectURL(audio.src);
  } catch (error) {
    console.error('Error playing audio: ', error);
  }
}
