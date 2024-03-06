using System.Collections.ObjectModel;
using System.ComponentModel;
using Xamarin.Forms;

namespace XAMPLUI.ViewModel
{
    public class MainPageViewModel : INotifyPropertyChanged
    {
        public MainPageViewModel()
        {
            EraseCommand = new Command(() =>
            {
                AllNotes.Clear();
            });

            SaveCommand = new Command(() =>
            {
                AllNotes.Add(TheNote);

                TheNote = string.Empty;
            });
        }

        public ObservableCollection<string> AllNotes { get; set; } = new ObservableCollection<string>();

        public event PropertyChangedEventHandler PropertyChanged;

        private string theNote;
        public string TheNote
        {
            get => theNote;
            set
            {
                theNote = value;
                PropertyChangedEventArgs args = new PropertyChangedEventArgs(nameof(TheNote));
                PropertyChanged?.Invoke(this, args);
            }
        }

        public Command SaveCommand { get; }

        public Command EraseCommand { get; }

    }
}