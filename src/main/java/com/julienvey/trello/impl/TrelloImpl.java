package com.julienvey.trello.impl;

import static com.julienvey.trello.impl.TrelloUrl.ADD_ATTACHMENT_TO_CARD;
import static com.julienvey.trello.impl.TrelloUrl.ADD_CHECKITEMS_TO_CHECKLIST;
import static com.julienvey.trello.impl.TrelloUrl.ADD_COMMENT_TO_CARD;
import static com.julienvey.trello.impl.TrelloUrl.ADD_EXISTING_LABEL_TO_CARD;
import static com.julienvey.trello.impl.TrelloUrl.ADD_LABEL_TO_CARD;
import static com.julienvey.trello.impl.TrelloUrl.ADD_MEMBER_TO_BOARD;
import static com.julienvey.trello.impl.TrelloUrl.ADD_MEMBER_TO_BOARD_BY_ID;
import static com.julienvey.trello.impl.TrelloUrl.ADD_MEMBER_TO_CARD;
import static com.julienvey.trello.impl.TrelloUrl.CREATE_CARD;
import static com.julienvey.trello.impl.TrelloUrl.CREATE_CHECKLIST;
import static com.julienvey.trello.impl.TrelloUrl.CREATE_LABEL;
import static com.julienvey.trello.impl.TrelloUrl.DELETE_ATTACHMENT;
import static com.julienvey.trello.impl.TrelloUrl.DELETE_CARD;
import static com.julienvey.trello.impl.TrelloUrl.DELETE_LABEL;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_BOARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_CARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_ENTITIES;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_LIST;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_MEMBER;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_MEMBER_CREATOR;
import static com.julienvey.trello.impl.TrelloUrl.GET_ACTION_ORGANIZATION;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_ACTIONS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_CARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_CARDS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_CHECKLISTS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_LABELS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_LISTS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_MEMBERS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_MEMBERSHIPS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_MEMBER_CARDS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_MYPREFS;
import static com.julienvey.trello.impl.TrelloUrl.GET_BOARD_ORGANIZATION;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD_ACTIONS;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD_ATTACHMENT;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD_ATTACHMENTS;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD_BOARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD_CHECKLIST;
import static com.julienvey.trello.impl.TrelloUrl.GET_CARD_MEMBERS;
import static com.julienvey.trello.impl.TrelloUrl.GET_CHECK_LIST;
import static com.julienvey.trello.impl.TrelloUrl.GET_CUSTOM_FIELD;
import static com.julienvey.trello.impl.TrelloUrl.GET_CUSTOM_FIELDS;
import static com.julienvey.trello.impl.TrelloUrl.GET_LABEL;
import static com.julienvey.trello.impl.TrelloUrl.GET_LIST;
import static com.julienvey.trello.impl.TrelloUrl.GET_LIST_CARDS;
import static com.julienvey.trello.impl.TrelloUrl.GET_MEMBER;
import static com.julienvey.trello.impl.TrelloUrl.GET_MEMBER_ACTIONS;
import static com.julienvey.trello.impl.TrelloUrl.GET_MEMBER_BOARDS;
import static com.julienvey.trello.impl.TrelloUrl.GET_MEMBER_CARDS;
import static com.julienvey.trello.impl.TrelloUrl.GET_ORGANIZATION;
import static com.julienvey.trello.impl.TrelloUrl.GET_ORGANIZATION_BOARD;
import static com.julienvey.trello.impl.TrelloUrl.GET_ORGANIZATION_MEMBER;
import static com.julienvey.trello.impl.TrelloUrl.REMOVE_MEMBER_FROM_BOARD;
import static com.julienvey.trello.impl.TrelloUrl.REMOVE_MEMBER_FROM_CARD;
import static com.julienvey.trello.impl.TrelloUrl.UPDATE_CARD;
import static com.julienvey.trello.impl.TrelloUrl.UPDATE_CARD_COMMENT;
import static com.julienvey.trello.impl.TrelloUrl.UPDATE_CUSTOM_FIELD_ITEM;
import static com.julienvey.trello.impl.TrelloUrl.UPDATE_LABEL;
import static com.julienvey.trello.impl.TrelloUrl.createUrl;
import static com.julienvey.trello.impl.TrelloUrl.createUrlWithNoArgs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.julienvey.trello.ListNotFoundException;
import com.julienvey.trello.NotFoundException;
import com.julienvey.trello.Trello;
import com.julienvey.trello.TrelloBadRequestException;
import com.julienvey.trello.TrelloHttpClient;
import com.julienvey.trello.domain.Action;
import com.julienvey.trello.domain.AddMemberToBoardResult;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.Attachment;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CardWithActions;
import com.julienvey.trello.domain.CheckItem;
import com.julienvey.trello.domain.CheckList;
import com.julienvey.trello.domain.CustomField;
import com.julienvey.trello.domain.CustomFieldItem;
import com.julienvey.trello.domain.Entity;
import com.julienvey.trello.domain.Label;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.domain.MemberType;
import com.julienvey.trello.domain.MyPrefs;
import com.julienvey.trello.domain.Organization;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.domain.TrelloEntity;
import com.julienvey.trello.impl.domaininternal.Comment;
import com.julienvey.trello.impl.http.JDKTrelloHttpClient;

public class TrelloImpl implements Trello {

	private final TrelloHttpClient httpClient;

	private final String applicationKey;

	private final String accessToken;

	private static Logger logger = LoggerFactory.getLogger(TrelloImpl.class);

	/**
	 * Deprecated - use another constructor which accepts an instance of `TrelloHttpClient`
	 * instead of creating one that is tied to Spring Web Framework.
	 *
	 * @see #TrelloImpl(String, String, TrelloHttpClient)
	 */
	@Deprecated
	public TrelloImpl(final String applicationKey, final String accessToken) {
		this(applicationKey, accessToken, new JDKTrelloHttpClient());
	}


	public TrelloImpl(final String applicationKey, final String accessToken, final TrelloHttpClient httpClient) {
		this.applicationKey = applicationKey;
		this.accessToken = accessToken;
		this.httpClient = httpClient;
	}

	/* Boards */


	@Override
	public Board getBoard(final String boardId, final Argument... args) {
		final Board board = get(createUrl(GET_BOARD).params(args).asString(), Board.class, boardId);
		board.setInternalTrello(this);
		return board;
	}


	@Override
	public List<Action> getBoardActions(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_ACTIONS).params(args).asString(), Action[].class, boardId));
	}


	@Override
	public List<Card> getBoardCards(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_CARDS).params(args).asString(), Card[].class, boardId));
	}


	@Override
	public Card getBoardCard(final String boardId, final String cardId, final Argument... args) {
		final Card card = get(createUrl(GET_BOARD_CARD).params(args).asString(), Card.class, boardId, cardId);
		card.setInternalTrello(this);
		return card;
	}


	@Override
	public List<CheckList> getBoardChecklists(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_CHECKLISTS).params(args).asString(), CheckList[].class, boardId));
	}


	@Override
	public List<Label> getBoardLabels(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_LABELS).params(args).asString(), Label[].class, boardId));
	}


	@Override
	public List<TList> getBoardLists(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_LISTS).params(args).asString(), TList[].class, boardId));
	}


	@Override
	public List<Member> getBoardMembers(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_MEMBERS).params(args).asString(), Member[].class, boardId));
	}


	@Override
	public List<Card> getBoardMemberCards(final String boardId, final String memberId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_MEMBER_CARDS).params(args).asString(), Card[].class, boardId, memberId));
	}


	@Override
	public AddMemberToBoardResult addMemberToBoard(final String boardId, final String email, final MemberType type,
		final String fullName) {
		Objects.requireNonNull(boardId);
		Objects.requireNonNull(email);

		final Map<String, String> body = new HashMap<>(3);
		body.put("fullName", fullName);
		body.put("email", email);
		body.put("type", Optional.ofNullable(type).orElse(MemberType.NORMAL).value());

		final AddMemberToBoardResult result = put(createUrl(ADD_MEMBER_TO_BOARD).asString(), body,
			AddMemberToBoardResult.class, boardId);
		result.setInternalTrello(this);

		return result;
	}


	@Override
	public AddMemberToBoardResult addMemberToBoard(final String boardId, final String memberId, final MemberType type) {
		Objects.requireNonNull(boardId);
		Objects.requireNonNull(memberId);

		final var body = Collections.singletonMap("type", Optional.ofNullable(type).orElse(MemberType.NORMAL).value());

		final var result = put(createUrl(ADD_MEMBER_TO_BOARD_BY_ID).asString(), body, AddMemberToBoardResult.class, boardId,
			memberId);
		result.setInternalTrello(this);

		return result;
	}


	@Override
	public Board removeMemberFromBoard(final String boardId, final String memberId) {
		Objects.requireNonNull(boardId);
		Objects.requireNonNull(memberId);

		try {
			final Board board = delete(createUrl(REMOVE_MEMBER_FROM_BOARD).asString(), Board.class, boardId, memberId);
			board.setInternalTrello(this);
			return board;
		} catch (final TrelloBadRequestException e) {
			// Trello API uses very strange way to report this kind of problems.
			// we should rethrow proper exception
			if ("membership not found".equalsIgnoreCase(e.getMessage())) {
				throw new NotFoundException(
					String.format("User with member id %s is not member of %s board.", memberId, boardId));
			} else if (e.getMessage().contains("\"message\":\"Invalid id or name\"")) {
				throw new NotFoundException(
					String.format("User with memberId or username %s is not found.", memberId));
			}
			throw e;
		}
	}


	// FIXME Remove this method
	@Override
	@Deprecated
	public List<CardWithActions> getBoardMemberActivity(final String boardId, final String memberId, String actionFilter,
		final Argument... args) {
		if (actionFilter == null) {
			actionFilter = "all";
		}
		final Argument[] argsAndFilter = Arrays.copyOf(args, args.length + 1);
		argsAndFilter[args.length] = new Argument("actions", actionFilter);

		return asList(() -> get(createUrl(GET_BOARD_MEMBER_CARDS).params(argsAndFilter).asString(), CardWithActions[].class,
			boardId, memberId));
	}


	@Override
	public List<Member> getBoardMemberships(final String boardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_BOARD_MEMBERSHIPS).params(args).asString(), Member[].class, boardId));
	}


	@Override
	public MyPrefs getBoardMyPrefs(final String boardId) {
		final MyPrefs myPrefs = get(createUrl(GET_BOARD_MYPREFS).asString(), MyPrefs.class, boardId);
		myPrefs.setInternalTrello(this);
		return myPrefs;
	}


	@Override
	public Organization getBoardOrganization(final String boardId, final Argument... args) {
		final Organization organization = get(createUrl(GET_BOARD_ORGANIZATION).params(args).asString(), Organization.class,
			boardId);
		organization.setInternalTrello(this);
		return organization;
	}

	/* Action */


	@Override
	public Action getAction(final String actionId, final Argument... args) {
		final Action action = get(createUrl(GET_ACTION).params(args).asString(), Action.class, actionId);
		action.setInternalTrello(this);
		return action;
	}


	@Override
	public Board getActionBoard(final String actionId, final Argument... args) {
		final Board board = get(createUrl(GET_ACTION_BOARD).params(args).asString(), Board.class, actionId);
		board.setInternalTrello(this);
		return board;
	}


	@Override
	public Card getActionCard(final String actionId, final Argument... args) {
		final Card card = get(createUrl(GET_ACTION_CARD).params(args).asString(), Card.class, actionId);
		card.setInternalTrello(this);
		return card;
	}


	@Override
	public List<Entity> getActionEntities(final String actionId) {
		return asList(() -> get(createUrl(GET_ACTION_ENTITIES).asString(), Entity[].class, actionId));
	}


	@Override
	public TList getActionList(final String actionId, final Argument... args) {
		final TList tList = get(createUrl(GET_ACTION_LIST).params(args).asString(), TList.class, actionId);
		tList.setInternalTrello(this);
		return tList;
	}


	@Override
	public Member getActionMember(final String actionId, final Argument... args) {
		final Member member = get(createUrl(GET_ACTION_MEMBER).params(args).asString(), Member.class, actionId);
		member.setInternalTrello(this);
		return member;
	}


	@Override
	public Member getActionMemberCreator(final String actionId, final Argument... args) {
		final Member member = get(createUrl(GET_ACTION_MEMBER_CREATOR).params(args).asString(), Member.class, actionId);
		member.setInternalTrello(this);
		return member;
	}


	@Override
	public Organization getActionOrganization(final String actionId, final Argument... args) {
		final Organization organization = get(createUrl(GET_ACTION_ORGANIZATION).params(args).asString(), Organization.class,
			actionId);
		organization.setInternalTrello(this);
		return organization;
	}


	@Override
	public Card getCard(final String cardId, final Argument... args) {
		final Card card = get(createUrl(GET_CARD).params(args).asString(), Card.class, cardId);
		card.setInternalTrello(this);
		return card;
	}


	@Override
	public List<Action> getCardActions(final String cardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_CARD_ACTIONS).params(args).asString(), Action[].class, cardId));
	}


	@Override
	public List<Attachment> getCardAttachments(final String cardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_CARD_ATTACHMENTS).params(args).asString(), Attachment[].class, cardId));

	}


	@Override
	public List<Member> getCardMembers(final String cardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_CARD_MEMBERS).params(args).asString(), Member[].class, cardId));
	}


	@Override
	public List<CustomFieldItem> getCardCustomFields(final String cardId, final Argument... args) {
		final List<CustomFieldItem> customFieldItems = asList(
			() -> get(createUrl(GET_CUSTOM_FIELDS).params(args).asString(), CustomFieldItem[].class, cardId));
		for (final CustomFieldItem customFieldItem : customFieldItems) {
			customFieldItem.setCardId(cardId);
		}
		return customFieldItems;
	}


	@Override
	public Attachment getCardAttachment(final String cardId, final String attachmentId, final Argument... args) {
		final Attachment attachment = get(createUrl(GET_CARD_ATTACHMENT).params(args).asString(), Attachment.class, cardId,
			attachmentId);
		attachment.setInternalTrello(this);
		return attachment;
	}


	@Override
	public Board getCardBoard(final String cardId, final Argument... args) {
		final Board board = get(createUrl(GET_CARD_BOARD).params(args).asString(), Board.class, cardId);
		board.setInternalTrello(this);
		return board;
	}


	@Override
	public void deleteCard(final String cardId) {
		Objects.requireNonNull(cardId);
		delete(createUrlWithNoArgs(DELETE_CARD), Map.class, cardId);
	}


	@Override
	public List<CheckList> getCardChecklists(final String cardId, final Argument... args) {
		return asList(() -> get(createUrl(GET_CARD_CHECKLIST).params(args).asString(), CheckList[].class, cardId));
	}

	/* Lists */


	@Override
	public TList getList(final String listId, final Argument... args) {
		final TList tList = get(createUrl(GET_LIST).params(args).asString(), TList.class, listId);
		tList.setInternalTrello(this);
		return tList;
	}


	@Override
	public List<Card> getListCards(final String listId, final Argument... args) {
		return asList(() -> get(createUrl(GET_LIST_CARDS).params(args).asString(), Card[].class, listId));
	}


	/* Organizations */
	@Override
	public Organization getOrganization(final String organizationId, final Argument... args) {
		final Organization organization = get(createUrl(GET_ORGANIZATION).params(args).asString(), Organization.class,
			organizationId);
		organization.setInternalTrello(this);
		return organization;
	}


	@Override
	public List<Board> getOrganizationBoards(final String organizationId, final Argument... args) {
		return asList(() -> get(createUrl(GET_ORGANIZATION_BOARD).params(args).asString(), Board[].class, organizationId));
	}


	@Override
	public List<Member> getOrganizationMembers(final String organizationId, final Argument... args) {
		return asList(() -> get(createUrl(GET_ORGANIZATION_MEMBER).params(args).asString(), Member[].class, organizationId));
	}


	@Override
	public CustomField getCustomField(final String fieldId, final Argument... args) {
		return get(createUrl(GET_CUSTOM_FIELD).params(args).asString(), CustomField.class, fieldId);
	}


	@Override
	public Label getLabel(final String labelId, final Argument... args) {
		final Label label = get(createUrl(GET_LABEL).params(args).asString(), Label.class, labelId);
		return label.setInternalTrello(this);
	}


	@Override
	public Label createLabel(final Label label) {
		final Label createdLabel = postForObject(createUrlWithNoArgs(CREATE_LABEL), label, Label.class);
		return createdLabel.setInternalTrello(this);
	}


	@Override
	public Label updateLabel(final Label label) {
		final Label updatedLabel = put(createUrlWithNoArgs(UPDATE_LABEL), label, Label.class, label.getId());
		return updatedLabel.setInternalTrello(this);
	}


	@Override
	public void deleteLabel(final String labelId) {
		delete(createUrlWithNoArgs(DELETE_LABEL), Map.class, labelId);
	}

	/* CheckLists */


	@Override
	public CheckList getCheckList(final String checkListId, final Argument... args) {
		final CheckList checkList = get(createUrl(GET_CHECK_LIST).params(args).asString(), CheckList.class, checkListId);
		checkList.setInternalTrello(this);
		return checkList;
	}


	@Override
	public CheckList createCheckList(final String cardId, final CheckList checkList) {
		checkList.setIdCard(cardId);
		final CheckList createdCheckList = postForObject(createUrl(CREATE_CHECKLIST).asString(), checkList, CheckList.class);
		createdCheckList.setInternalTrello(this);
		return createdCheckList;
	}


	@Override
	public void createCheckItem(final String checkListId, final CheckItem checkItem) {
		postForLocation(createUrl(ADD_CHECKITEMS_TO_CHECKLIST).asString(), checkItem, checkListId);
	}

	/* Others */


	@Override
	public Card createCard(final String listId, final Card card) {
		card.setIdList(listId);
		try {
			final Card createdCard = postForObject(createUrl(CREATE_CARD).asString(), card, Card.class);
			createdCard.setInternalTrello(this);
			return createdCard;
		} catch (final TrelloBadRequestException e) {
			throw decodeException(card, e);
		}
	}


	@Override
	public Card updateCard(final Card card) {
		try {
			final Card put = put(createUrl(UPDATE_CARD).asString(), card, Card.class, card.getId());
			put.setInternalTrello(this);
			return put;
		} catch (final TrelloBadRequestException e) {
			throw decodeException(card, e);
		}
	}


	private static TrelloBadRequestException decodeException(final Card card, final TrelloBadRequestException e) {
		if (e.getMessage().contains("invalid value for idList")) {
			return new ListNotFoundException(card.getIdList());
		}
		if (e instanceof NotFoundException) {
			return new NotFoundException(
				"Card with id " + card.getId() + " is not found. It may have been deleted in Trello");
		}
		return e;
	}


	@Override
	// FIXME Remove this method
	@Deprecated
	public Member getBasicMemberInformation(final String username) {
		final Member member = get(createUrl(GET_MEMBER).params(new Argument("fields", "username,fullName")).asString(),
			Member.class, username);
		member.setInternalTrello(this);
		return member;
	}


	@Override
	public Member getMemberInformation(final String username) {
		final Member member = get(createUrl(GET_MEMBER).asString(), Member.class, username);
		member.setInternalTrello(this);
		return member;
	}


	@Override
	public List<Board> getMemberBoards(final String userId, final Argument... args) {
		return asList(() -> get(createUrl(GET_MEMBER_BOARDS).params(args).asString(), Board[].class, userId));
	}


	@Override
	public List<Card> getMemberCards(final String userId, final Argument... args) {
		return asList(() -> get(createUrl(GET_MEMBER_CARDS).params(args).asString(), Card[].class, userId));
	}


	@Override
	public List<Action> getMemberActions(final String userId, final Argument... args) {
		return asList(() -> get(createUrl(GET_MEMBER_ACTIONS).params(args).asString(), Action[].class, userId));
	}


	@Override
	public void addLabelsToCard(final String idCard, final String[] labels) {
		for (final String labelName : labels) {
			final Label label = new Label();
			label.setName(labelName);
			postForObject(createUrl(ADD_LABEL_TO_CARD).asString(), label, Label.class, idCard);
		}
	}


	@Override
	public CustomFieldItem updateCustomFieldItem(final String idCard, CustomFieldItem customFieldItem) {
		Objects.requireNonNull(idCard);
		Objects.requireNonNull(customFieldItem);

		customFieldItem = put(createUrlWithNoArgs(UPDATE_CUSTOM_FIELD_ITEM), customFieldItem, CustomFieldItem.class, idCard,
			customFieldItem.getIdCustomField());
		customFieldItem.setCardId(idCard);

		return customFieldItem;
	}


	@Override
	public List<String> addLabelToCard(final String cardId, final String labelId) {
		Objects.requireNonNull(cardId);
		Objects.requireNonNull(labelId);

		return Arrays.asList(postForObject(createUrlWithNoArgs(ADD_EXISTING_LABEL_TO_CARD),
			Collections.singletonMap("value", labelId), String[].class, cardId));
	}


	@Override
	public void addCommentToCard(final String idCard, final String comment) {
		postForObject(createUrl(ADD_COMMENT_TO_CARD).asString(), new Comment(comment), Comment.class, idCard);
	}


	@Override
	public Action updateComment(final String idCard, final String commentActionId, final String text) {
		return put(createUrlWithNoArgs(UPDATE_CARD_COMMENT), new Comment(text), Action.class, idCard, commentActionId);
	}


	@Override
	public void addAttachmentToCard(final String idCard, final File file) {
		postFileForObject(createUrl(ADD_ATTACHMENT_TO_CARD).asString(), file, Attachment.class, idCard);
	}


	@Override
	public void addUrlAttachmentToCard(final String idCard, final String url) {
		postForObject(createUrl(ADD_ATTACHMENT_TO_CARD).asString(), new Attachment(url), Attachment.class, idCard);
	}


	@Override
	public void deleteAttachment(final String idCard, final String attachmentId) {
		delete(createUrlWithNoArgs(DELETE_ATTACHMENT), Map.class, idCard, attachmentId);
	}


	@Override
	public List<Member> addMemberToCard(final String idCard, final String userId) {
		return asList(() -> postForObject(createUrl(ADD_MEMBER_TO_CARD).asString(), Collections.singletonMap("value", userId),
			Member[].class, idCard));
	}


	@Override
	public List<Member> removeMemberFromCard(final String idCard, final String userId) {
		return asList(() -> delete(createUrl(REMOVE_MEMBER_FROM_CARD).asString(), Member[].class, idCard, userId));
	}

	/* internal methods */


	private <T> T postFileForObject(final String url, final File file, final Class<T> objectClass, final String... params) {
		logger.debug("PostFileForObject request on Trello API at url {} for class {} with params {}", url,
			objectClass.getCanonicalName(), params);

		return httpClient.postFileForObject(url, file, objectClass, enrichParams(params));
	}


	private <T> T postForObject(final String url, final Object object, final Class<T> objectClass, final String... params) {
		logger.debug("PostForObject request on Trello API at url {} for class {} with params {}", url,
			objectClass.getCanonicalName(), params);
		return httpClient.postForObject(url, object, objectClass, enrichParams(params));
	}


	private void postForLocation(final String url, final Object object, final String... params) {
		logger.debug("PostForLocation request on Trello API at url {} for class {} with params {}", url,
			object.getClass().getCanonicalName(), params);
		httpClient.postForLocation(url, object, enrichParams(params));
	}


	private <T> T get(final String url, final Class<T> objectClass, final String... params) {
		logger.debug("Get request on Trello API at url {} for class {} with params {}", url, objectClass.getCanonicalName(),
			params);
		return httpClient.get(url, objectClass, enrichParams(params));
	}


	private <T> T put(final String url, final Object object, final Class<T> objectClass, final String... params) {
		logger.debug("Put request on Trello API at url {} for class {} with params {}", url,
			object.getClass().getCanonicalName(), params);
		return httpClient.putForObject(url, object, objectClass, enrichParams(params));
	}


	private <T> T delete(final String url, final Class<T> responseType, final String... params) {
		logger.debug("Delete request on Trello API at url {} for class {} with params {}", url,
			responseType.getClass().getCanonicalName(), params);
		return httpClient.delete(url, responseType, enrichParams(params));
	}


	private String[] enrichParams(final String[] params) {
		final var paramList = new ArrayList<>(Arrays.asList(params));
		paramList.add(applicationKey);
		paramList.add(accessToken);
		return paramList.toArray(new String[paramList.size()]);
	}


	private <T extends TrelloEntity> List<T> asList(final Supplier<T[]> responseSupplier) {
		return Arrays.stream(responseSupplier.get()).peek(t -> t.setInternalTrello(this)).collect(Collectors.toList());
	}
}
