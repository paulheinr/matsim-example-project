import matsim

def count_entered_link_events(events_file: str) -> int:
    count = 0

    events = matsim.event_reader(events_file, types="entered link")

    for event in events:
        count += 1

    return count


if __name__ == "__main__":
    events_file = "../../../output/output_events.xml.zst"
    total = count_entered_link_events(events_file)
    print(f"Total link enter events: {total}")
    print()
    print("_________")
    print("Further documentation can be found here: https://github.com/matsim-vsp/matsim-python-tools")