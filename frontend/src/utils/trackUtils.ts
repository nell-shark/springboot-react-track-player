import { trackService } from '@services/trackService';

const audio = new Audio();

export async function playAudioElement(trackId: string) {
  try {
    const { data } = await trackService.getTrackById(trackId);
    const binaryString = atob(data.bytes!);
    const uint8Array = new Uint8Array(binaryString.length);

    for (let i = 0; i < binaryString.length; i++) uint8Array[i] = binaryString.charCodeAt(i);

    const audioBlob = new Blob([uint8Array], { type: 'audio/mp3' }); // TODO: mpeg
    audio.src = URL.createObjectURL(audioBlob);
    await audio.play();
  } catch (error) {
    console.error('Error playing audio:', error);
  }
}

export function pauseAudioElement() {
  audio.pause();
}